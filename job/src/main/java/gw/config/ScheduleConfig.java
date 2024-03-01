package gw.config;

import com.gw.handler.CustomThreadPoolTaskScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.task.TaskSchedulerBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@RequiredArgsConstructor
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {

  private final CustomThreadPoolTaskScheduler customThreadPoolTaskScheduler;

  private final TaskSchedulerBuilder builder;


  @Override
  public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

    ThreadPoolTaskScheduler taskScheduler = builder.configure(customThreadPoolTaskScheduler);
    taskRegistrar.setTaskScheduler(taskScheduler);
  }
}
