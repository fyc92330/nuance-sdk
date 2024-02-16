package org.chun.hepler;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.chun.constant.RedisPrefix;
import org.chun.exception.GdsRedisException;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;


public class RedisList<T> extends RedisJsonParser<T> {

  private final ListOperations<String, String> opsForList;


  public RedisList(RedisTemplate<String, String> redisTemplate, TypeReference<T> typeReference) {

    super(typeReference);
    this.opsForList = redisTemplate.opsForList();
  }


  public List<T> range(RedisPrefix prefix, String key, long start, long end) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    return convert(opsForList.range(prefix.getRedisKey(key), start, end));
  }


  public T firstPop(RedisPrefix prefix, String key) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    return convert(opsForList.leftPop(prefix.getRedisKey(key)));
  }


  public T firstPop(RedisPrefix prefix, String key, long timeout) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (timeout <= 0) {
      throw new GdsRedisException("timeout must > 0");
    }

    return convert(opsForList.leftPop(prefix.getRedisKey(key), timeout, TimeUnit.MILLISECONDS));
  }


  public List<T> firstPop(RedisPrefix prefix, String key, int count) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (count <= 0) {
      throw new GdsRedisException("count must > 0");
    }

    return convert(opsForList.leftPop(prefix.getRedisKey(key), count));
  }


  public T lastPop(RedisPrefix prefix, String key) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    return convert(opsForList.rightPop(prefix.getRedisKey(key)));
  }


  public T lastPop(RedisPrefix prefix, String key, long timeout) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (timeout <= 0) {
      throw new GdsRedisException("timeout must > 0");
    }

    return convert(opsForList.rightPop(prefix.getRedisKey(key), timeout, TimeUnit.MILLISECONDS));
  }


  public List<T> lastPop(RedisPrefix prefix, String key, int count) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (count <= 0) {
      throw new GdsRedisException("count must > 0");
    }

    return convert(opsForList.rightPop(prefix.getRedisKey(key), count));
  }


  public Long firstPush(RedisPrefix prefix, String key, T value) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (null == value) {
      return 0L;
    }

    return opsForList.leftPush(prefix.getRedisKey(key), format(value));
  }


  public Long firstPushAll(RedisPrefix prefix, String key, Collection<T> values) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (CollectionUtils.isEmpty(values)) {
      return 0L;
    }

    return opsForList.leftPushAll(prefix.getRedisKey(key), format2List(values));
  }


  public Long firstPushAll(RedisPrefix prefix, String key, T... values) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (ArrayUtils.isEmpty(values)) {
      return 0L;
    }

    return opsForList.leftPushAll(prefix.getRedisKey(key), format2List(values));
  }


  public Long lastPush(RedisPrefix prefix, String key, T value) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (null == value) {
      return 0L;
    }

    return opsForList.rightPush(prefix.getRedisKey(key), format(value));
  }


  public Long lastPushAll(RedisPrefix prefix, String key, Collection<T> values) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (CollectionUtils.isEmpty(values)) {
      return 0L;
    }

    return opsForList.rightPushAll(prefix.getRedisKey(key), format2List(values));
  }


  public Long lastPushAll(RedisPrefix prefix, String key, T... values) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (ArrayUtils.isEmpty(values)) {
      return 0L;
    }

    return opsForList.rightPushAll(prefix.getRedisKey(key), format2List(values));
  }


  public void set(RedisPrefix prefix, String key, int index, T value) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (null == value) {
      return;
    }

    opsForList.set(prefix.getRedisKey(key), index, format(value));
  }


  public Long size(RedisPrefix prefix, String key) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    return opsForList.size(prefix.getRedisKey(key));
  }


  public Long remove(RedisPrefix prefix, String key, long signal, T value) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    // 0:全部符合的都移除
    // 1:從最後面開始移除一個符合條件的
    // -1:從最前面開始移除一個符合條件的
    if (signal != 0 && signal != 1 && signal != -1) {
      throw new GdsRedisException("signal is incorrect");
    }

    return opsForList.remove(prefix.getRedisKey(key), signal, format(value));
  }

}
