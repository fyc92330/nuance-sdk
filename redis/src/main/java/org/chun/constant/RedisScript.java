package org.chun.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RedisScript {

  UNLOCK("scripts/unlock.lua"),

  ;

  private final String path;

}
