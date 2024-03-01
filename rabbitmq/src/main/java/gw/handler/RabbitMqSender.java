package gw.handler;

import com.gw.constant.RabbitAction;
import com.gw.constant.RabbitDefine;
import com.gw.constant.RabbitExchange;
import com.gw.constant.RabbitGroup;
import com.gw.dto.RabbitReq;
import com.gw.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class RabbitMqSender {

  private final RabbitTemplate rabbitTemplate;


  public void unicast(RabbitGroup group, String name, RabbitAction action, Object body) {

    RabbitReq req = new RabbitReq();
    req.setAction(action.getName());
    req.setBody(body);

    rabbitTemplate.convertAndSend(RabbitDefine.EXCLUSIVE.getQueueName(group.getName(), name), JsonUtils.SYSTEM.toJson(req));
  }


  public void unicast(RabbitGroup group, RabbitAction action, Object body) {

    RabbitReq req = new RabbitReq();
    req.setAction(action.getName());
    req.setBody(body);

    rabbitTemplate.convertAndSend(RabbitDefine.SHARE.getQueueName(group.getName()), JsonUtils.SYSTEM.toJson(req));
  }


  public void broadcast(RabbitGroup group, RabbitAction action, Object body) {

    RabbitReq req = new RabbitReq();
    req.setAction(action.getName());
    req.setBody(body);

    rabbitTemplate.convertAndSend(RabbitExchange.DESIGNATED_GROUP.getName(group.getName()), "", JsonUtils.SYSTEM.toJson(req));
  }


  public void broadcast(RabbitAction action, Object value) {

    RabbitReq req = new RabbitReq();
    req.setAction(action.getName());
    req.setBody(value);

    rabbitTemplate.convertAndSend(RabbitExchange.EVERYONE.name(), "", JsonUtils.SYSTEM.toJson(req));
  }
}
