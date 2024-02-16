package org.chun.hepler;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.chun.constant.RedisPrefix;
import org.chun.exception.GdsRedisException;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;


public class RedisHash<T> extends RedisJsonParser<T> {

  private final HashOperations<String, String, String> opsForHash;


  public RedisHash(RedisTemplate<String, String> redisTemplate, TypeReference<T> typeReference) {

    super(typeReference);
    this.opsForHash = redisTemplate.opsForHash();
  }


  public T get(RedisPrefix prefix, String key, String hashKey) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (StringUtils.isBlank(hashKey)) {
      throw new GdsRedisException("hashKey is blank");
    }

    return convert(opsForHash.get(prefix.getRedisKey(key), hashKey));
  }


  public List<T> multiGet(RedisPrefix prefix, String key, Collection<String> hashKeys) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (CollectionUtils.isEmpty(hashKeys)) {
      return Collections.emptyList();
    }

    return convert(opsForHash.multiGet(prefix.getRedisKey(key), hashKeys));
  }


  public Map<String, T> entries(RedisPrefix prefix, String key) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    return convert(opsForHash.entries(prefix.getRedisKey(key)));
  }


  public Map<String, T> scan(RedisPrefix prefix, String key, int count) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (count <= 0) {
      return Collections.emptyMap();
    }

    ScanOptions scanOptions = ScanOptions.scanOptions().count(count).build();

    return convert2Map(opsForHash.scan(prefix.getRedisKey(key), scanOptions));
  }


  public Map<String, T> scan(RedisPrefix prefix, String key, String pattern, int count) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (StringUtils.isBlank(pattern)) {
      throw new GdsRedisException("pattern is blank");
    }

    ScanOptions scanOptions = ScanOptions.scanOptions().match(pattern).count(count).build();

    return convert2Map(opsForHash.scan(prefix.getRedisKey(key), scanOptions));
  }


  public Set<String> keys(RedisPrefix prefix, String key) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    return opsForHash.keys(prefix.getRedisKey(key));
  }


  public List<T> values(RedisPrefix prefix, String key) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    return convert(opsForHash.values(prefix.getRedisKey(key)));
  }


  public void put(RedisPrefix prefix, String key, String hashKey, T value) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (StringUtils.isBlank(hashKey)) {
      throw new GdsRedisException("hashKey is blank");
    }

    if (null == value) {
      return;
    }

    opsForHash.put(prefix.getRedisKey(key), hashKey, format(value));
  }


  public void putAll(RedisPrefix prefix, String key, Map<String, T> map) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (null == map || map.isEmpty()) {
      return;
    }

    opsForHash.putAll(prefix.getRedisKey(key), format2Map(map));
  }


  public Boolean setIfAbsent(RedisPrefix prefix, String key, String hashKey, T value) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (StringUtils.isBlank(hashKey)) {
      throw new GdsRedisException("hashKey is blank");
    }

    if (null == value) {
      return false;
    }

    return opsForHash.putIfAbsent(prefix.getRedisKey(key), hashKey, format(value));
  }


  public Long size(RedisPrefix prefix, String key) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    return opsForHash.size(prefix.getRedisKey(key));
  }


  public Long delete(RedisPrefix prefix, String key, Object... hashKeys) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (ArrayUtils.isEmpty(hashKeys)) {
      return 0L;
    }

    return opsForHash.delete(prefix.getRedisKey(key), hashKeys);
  }


  public Long increment(RedisPrefix prefix, String key, String hashKey, long delta) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (StringUtils.isBlank(hashKey)) {
      throw new GdsRedisException("hashKey is blank");
    }

    return opsForHash.increment(prefix.getRedisKey(key), hashKey, delta);
  }


  public Long decrement(RedisPrefix prefix, String key, String hashKey, long delta) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (StringUtils.isBlank(hashKey)) {
      throw new GdsRedisException("hashKey is blank");
    }

    return opsForHash.increment(prefix.getRedisKey(key), hashKey, -delta);
  }

}
