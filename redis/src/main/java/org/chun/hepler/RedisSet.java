package org.chun.hepler;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.chun.constant.RedisPrefix;
import org.chun.exception.GdsRedisException;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.SetOperations;

public class RedisSet<T> extends RedisJsonParser<T> {

  private final RedisTemplate<String, String> redisTemplate;

  private final SetOperations<String, String> opsForSet;


  public RedisSet(RedisTemplate<String, String> redisTemplate, TypeReference<T> typeReference) {

    super(typeReference);
    this.redisTemplate = redisTemplate;
    this.opsForSet = redisTemplate.opsForSet();
  }


  public List<T> members(RedisPrefix prefix, String key) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    return convert(opsForSet.members(prefix.getRedisKey(key)));
  }


  public Set<T> scan(RedisPrefix prefix, String key, int count) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (count <= 0) {
      return Collections.emptySet();
    }

    ScanOptions scanOptions = ScanOptions.scanOptions().count(count).build();

    return convert2Set(opsForSet.scan(prefix.getRedisKey(key), scanOptions));
  }


  public Boolean isMember(RedisPrefix prefix, String key, T value) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    return opsForSet.isMember(prefix.getRedisKey(key), format(value));
  }


  public T pop(RedisPrefix prefix, String key) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    return convert(opsForSet.pop(prefix.getRedisKey(key)));
  }


  public List<T> pop(RedisPrefix prefix, String key, int count) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (count <= 0) {
      return Collections.emptyList();
    }

    return convert(opsForSet.pop(prefix.getRedisKey(key), count));
  }


  public Long add(RedisPrefix prefix, String key, T value) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    return opsForSet.add(prefix.getRedisKey(key), format(value));
  }


  public Long add(RedisPrefix prefix, String key, T... values) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (ArrayUtils.isEmpty(values)) {
      return 0L;
    }

    RedisCallback<Boolean> callback = (connection) -> {

      for (T value : values) {

        connection.sAdd(prefix.getRedisKey(key).getBytes(), format(value).getBytes());
      }

      return null;
    };

    List<Object> results = redisTemplate.executePipelined(callback);
    if (results.isEmpty()) {
      return 0L;
    }

    Long count = 0L;
    for (Object value : results) {

      count += (Long) value;
    }

    return count;
  }


  public Long size(RedisPrefix prefix, String key) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    return opsForSet.size(prefix.getRedisKey(key));
  }


  public Long remove(RedisPrefix prefix, String key, T value) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    return opsForSet.remove(prefix.getRedisKey(key), format(value));
  }
}
