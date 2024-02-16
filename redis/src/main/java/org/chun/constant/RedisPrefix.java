package org.chun.constant;

public interface RedisPrefix {

  String getPrefix();

  String getRedisKey(String... keys);
}
