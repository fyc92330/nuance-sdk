package gw.handler;


import com.gw.dto.RabbitReq;
import com.gw.exception.GwRabbitException;
import com.gw.util.JsonUtils;
import com.gw.util.LogUtils;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RabbitMqReceiver {

  private static final Map<String, RabbitMqMessenger<?>> CALLBACK = new ConcurrentHashMap<>();

  private final List<RabbitMqMessenger<?>> messages;


  @PostConstruct
  public void init() {

    for (RabbitMqMessenger<?> rabbitMqMessenger : messages) {

      String name = rabbitMqMessenger.action().getName();
      if (CALLBACK.containsKey(name)) {
        throw new GwRabbitException("duplicate mq action:" + name);
      }

      CALLBACK.put(name, rabbitMqMessenger);
    }
  }


  @RabbitListener(queues = "#{exclusiveQueue.getName()}")
  @RabbitHandler
  protected void onConsumeExclusiveQueue(String message) {

    LogUtils.RABBIT.debug("onConsumeExclusiveQueue Receive:{}", message);
    convert(message);
  }


  @RabbitListener(queues = "#{shareQueue.getName()}")
  @RabbitHandler
  protected void onConsumeShareQueue(String message) {

    LogUtils.RABBIT.debug("onConsumeShareQueue Receive:{}", message);
    convert(message);
  }


  private void convert(String message) {

    RabbitReq req = JsonUtils.SYSTEM.fromJson(message, RabbitReq.class);
    if (null == req) {
      return;
    }

    String action = req.getAction();
    RabbitMqMessenger<?> rabbitMqMessenger = CALLBACK.get(action);
    if (null == rabbitMqMessenger) {
      LogUtils.RABBIT.error("rabbitMqMessenger missing. action:{}", action);
      return;
    }

    Object body = req.getBody();
    rabbitMqMessenger.onMessage(body);
  }
}
