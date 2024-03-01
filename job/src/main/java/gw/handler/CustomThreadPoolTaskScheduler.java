package gw.handler;

import com.gw.util.LogUtils;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.FixedDelayTask;
import org.springframework.scheduling.config.FixedRateTask;
import org.springframework.scheduling.config.Task;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
public class CustomThreadPoolTaskScheduler extends ThreadPoolTaskScheduler {

  private final Map<String, ScheduledFuture<?>> scheduledFutures = new ConcurrentHashMap<>();

  private final Map<String, Task> scheduleTasks = new ConcurrentHashMap<>();


  @Override
  public ScheduledFuture<?> schedule(Runnable task, Trigger trigger) {

    CronTask cronTask = new CronTask(task, (CronTrigger) trigger);
    scheduleTasks.put(task.toString(), cronTask);

    ScheduledFuture<?> scheduledFuture = super.schedule(task, trigger);
    scheduledFutures.put(task.toString(), scheduledFuture);

    return scheduledFuture;
  }


  @Override
  public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, Date startTime, long period) {

    FixedRateTask fixedRateTask = new FixedRateTask(task, period, 0);
    scheduleTasks.put(task.toString(), fixedRateTask);

    ScheduledFuture<?> scheduledFuture = super.scheduleAtFixedRate(task, startTime, period);
    scheduledFutures.put(task.toString(), scheduledFuture);

    return scheduledFuture;
  }


  @Override
  public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, long period) {

    FixedRateTask fixedRateTask = new FixedRateTask(task, period, 0);
    scheduleTasks.put(task.toString(), fixedRateTask);

    ScheduledFuture<?> scheduledFuture = super.scheduleAtFixedRate(task, period);
    scheduledFutures.put(task.toString(), scheduledFuture);

    return scheduledFuture;
  }


  @Override
  public ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, Date startTime, long delay) {

    FixedDelayTask fixedDelayTask = new FixedDelayTask(task, delay, 0);
    scheduleTasks.put(task.toString(), fixedDelayTask);

    ScheduledFuture<?> scheduledFuture = super.scheduleWithFixedDelay(task, startTime, delay);
    scheduledFutures.put(task.toString(), scheduledFuture);

    return scheduledFuture;
  }


  @Override
  public ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, long delay) {

    FixedDelayTask fixedDelayTask = new FixedDelayTask(task, delay, 0);
    scheduleTasks.put(task.toString(), fixedDelayTask);

    ScheduledFuture<?> scheduledFuture = super.scheduleWithFixedDelay(task, delay);
    scheduledFutures.put(task.toString(), scheduledFuture);

    return scheduledFuture;
  }


  @Override
  public void shutdown() {

    // force interrupt
    super.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
    super.shutdown();
    super.destroy();

    LogUtils.SCHEDULE.info("schedule all stopped");
  }


  public void start(String taskId) {

    Task task = scheduleTasks.get(taskId);
    if (null == task) {
      return;
    }

    ScheduledFuture<?> scheduledFuture = scheduledFutures.get(taskId);
    if (null != scheduledFuture) {
      return;
    }

    LogUtils.SCHEDULE.info("schedule start:{}", taskId);

    if (task instanceof FixedRateTask) {

      FixedRateTask fixedRateTask = (FixedRateTask) task;
      scheduleAtFixedRate(fixedRateTask.getRunnable(), fixedRateTask.getInterval());
      return;
    }

    if (task instanceof FixedDelayTask) {

      FixedDelayTask fixedDelayTask = (FixedDelayTask) task;
      scheduleWithFixedDelay(fixedDelayTask.getRunnable(), fixedDelayTask.getInterval());
      return;
    }

    CronTask cronTask = (CronTask) task;
    schedule(cronTask.getRunnable(), cronTask.getTrigger());
  }


  public void stop(String taskId) {

    ScheduledFuture<?> scheduledFuture = scheduledFutures.get(taskId);
    if (null == scheduledFuture) {
      return;
    }

    scheduledFuture.cancel(false);
    scheduledFutures.remove(taskId);

    LogUtils.SCHEDULE.info("schedule stop:{}", taskId);
  }


  public Set<String> getRunningTaskIds() {

    return Collections.unmodifiableSet(scheduledFutures.keySet());
  }


  public LocalDateTime getNextExecuteTime(String taskId) {

    ScheduledFuture<?> scheduledFuture = scheduledFutures.get(taskId);
    if (null == scheduledFuture) {
      return null;
    }

    long delay = scheduledFuture.getDelay(TimeUnit.MILLISECONDS);

    return LocalDateTime.now().plus(delay, ChronoUnit.MILLIS);
  }
}
