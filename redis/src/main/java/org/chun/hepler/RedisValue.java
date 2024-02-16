package org.chun.hepler;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.chun.constant.RedisPrefix;
import org.chun.exception.GdsRedisException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;


public class RedisValue<T> extends RedisJsonParser<T> {


  private final ValueOperations<String, String> opsForValue;


  public RedisValue(RedisTemplate<String, String> redisTemplate, TypeReference<T> typeReference) {

    super(typeReference);
    this.opsForValue = redisTemplate.opsForValue();
  }


  public T get(RedisPrefix prefix, String key) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    return convert(opsForValue.get(prefix.getRedisKey(key)));
  }


  public List<T> multiGet(RedisPrefix prefix, Collection<String> keys) {

    if (CollectionUtils.isEmpty(keys)) {
      return Collections.emptyList();
    }

    return convert(opsForValue.multiGet(format2List(prefix, keys)));
  }


  public void set(RedisPrefix prefix, String key, T value) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (null == value) {
      return;
    }

    opsForValue.set(prefix.getRedisKey(key), format(value));
  }


  public void set(RedisPrefix prefix, String key, T value, long timeout) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (null == value) {
      return;
    }

    if (timeout <= 0) {
      return;
    }

    opsForValue.set(prefix.getRedisKey(key), format(value), timeout, TimeUnit.MILLISECONDS);
  }


  public void multiSet(RedisPrefix prefix, Map<String, T> map) {

    if (null == map || map.isEmpty()) {
      return;
    }

    opsForValue.multiSet(format2Map(prefix, map));
  }


  public Boolean setIfAbsent(RedisPrefix prefix, String key, T value) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (null == value) {
      return false;
    }

    return opsForValue.setIfAbsent(prefix.getRedisKey(key), format(value));
  }


  public Boolean setIfAbsent(RedisPrefix prefix, String key, T value, long timeout) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (null == value) {
      return false;
    }

    if (timeout <= 0) {
      return false;
    }

    return opsForValue.setIfAbsent(prefix.getRedisKey(key), format(value), timeout, TimeUnit.MILLISECONDS);
  }


  public Boolean multiSetIfAbsent(RedisPrefix prefix, Map<String, T> map) {

    if (null == map || map.isEmpty()) {
      return false;
    }

    return opsForValue.multiSetIfAbsent(format2Map(prefix, map));
  }


  public Long increment(RedisPrefix prefix, String key, long delta) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    return opsForValue.increment(prefix.getRedisKey(key), delta);
  }


  public Long decrement(RedisPrefix prefix, String key, long delta) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    return opsForValue.decrement(prefix.getRedisKey(key), delta);
  }

}
