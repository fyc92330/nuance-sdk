package gw.constant;

import lombok.Getter;


@Getter
public enum RabbitDefine {
  EXCLUSIVE,
  SHARE,

  ;


  public String getQueueName(String group) {

    return String.join(":", this.name(), group).toLowerCase();
  }


  public String getQueueName(String group, String name) {

    return String.join("_", this.name(), group, name).toLowerCase();
  }
}
