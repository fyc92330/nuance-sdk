package gw.constant;

public enum RabbitExchange {

  EVERYONE,

  DESIGNATED_GROUP,
  ;


  public String getName(String group) {

    return String.join("_", this.name(), group);
  }

}
