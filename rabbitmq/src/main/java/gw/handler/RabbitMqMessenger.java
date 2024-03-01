package gw.handler;


import com.fasterxml.jackson.databind.JavaType;
import com.gw.constant.RabbitAction;
import com.gw.util.JsonUtils;
import java.lang.reflect.ParameterizedType;
import org.springframework.stereotype.Component;

@Component
public abstract class RabbitMqMessenger<T> {

  private final JavaType javaType = JsonUtils.SYSTEM.toJavaType(((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);


  public abstract RabbitAction action();

  public abstract void doProcess(T object);


  protected void onMessage(Object body) {

    doProcess(JsonUtils.SYSTEM.convert(body, javaType));
  }


}
