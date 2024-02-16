package org.chun.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RedisCommonKey implements RedisPrefix {

  TEST("gds:redis:test"),
  LOCK("gds:redis:lock"),
  ;

  private final String prefix;


  @Override
  public String getRedisKey(String... keys) {

    return getPrefix() + ":" + String.join(":", keys);
  }
}
