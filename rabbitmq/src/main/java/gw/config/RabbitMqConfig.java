package gw.config;

import com.gw.constant.RabbitDefine;
import com.gw.constant.RabbitExchange;
import java.net.InetAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class RabbitMqConfig {

  @Value("${group-id}")
  private String groupId;


  @Bean("exclusiveQueue")
  public Queue exclusiveQueue() throws Exception {

    String ip = InetAddress.getLocalHost().getHostAddress();
    System.out.println("ip=" + ip);

    return new Queue(RabbitDefine.EXCLUSIVE.getQueueName(groupId, ip), true, true, false);
  }


  @Bean("shareQueue")
  public Queue shareQueue() {

    return new Queue(RabbitDefine.SHARE.getQueueName(groupId));
  }


  @Bean("everyoneExchange")
  public FanoutExchange everyoneExchange() {

    return new FanoutExchange(RabbitExchange.EVERYONE.name());
  }


  @Bean("designatedGroupExchange")
  public FanoutExchange designatedGroupExchange() {

    return new FanoutExchange(RabbitExchange.DESIGNATED_GROUP.getName(groupId));
  }


  @Bean
  public Binding everyoneExchangeBinding(@Qualifier("exclusiveQueue") Queue queue, @Qualifier("everyoneExchange") FanoutExchange fanoutExchange) {

    return BindingBuilder.bind(queue).to(fanoutExchange);
  }


  @Bean
  public Binding designatedGroupExchangeBinding(@Qualifier("exclusiveQueue") Queue queue, @Qualifier("designatedGroupExchange") FanoutExchange fanoutExchange) {

    return BindingBuilder.bind(queue).to(fanoutExchange);
  }
}
