package org.chun.hepler;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.chun.constant.RedisPrefix;
import org.chun.exception.GdsRedisException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;


public class RedisOps extends RedisJsonParser<String> {

  private final RedisTemplate<String, String> redisTemplate;


  public RedisOps(RedisTemplate<String, String> redisTemplate) {

    super(new TypeReference<String>() {

    });

    this.redisTemplate = redisTemplate;
  }


  public Boolean delete(RedisPrefix prefix, String key) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    return redisTemplate.delete(prefix.getRedisKey(key));
  }


  public Long delete(RedisPrefix prefix, Collection<String> keys) {

    if (CollectionUtils.isEmpty(keys)) {
      return 0L;
    }

    return redisTemplate.delete(format2List(prefix, keys));
  }


  public Boolean unlink(RedisPrefix prefix, String key) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    return redisTemplate.unlink(prefix.getRedisKey(key));
  }


  public Long unlink(RedisPrefix prefix, Collection<String> keys) {

    if (CollectionUtils.isEmpty(keys)) {
      return 0L;
    }

    return redisTemplate.unlink(format2List(prefix, keys));
  }


  public Set<String> keys(String pattern) {

    if (StringUtils.isBlank(pattern)) {
      throw new GdsRedisException("pattern is blank");
    }

    return redisTemplate.keys(pattern);
  }


  public Set<String> scan(int count) {

    if (count <= 0) {
      return Collections.emptySet();
    }

    ScanOptions scanOptions = ScanOptions.scanOptions().count(count).build();

    return convert2Set(redisTemplate.scan(scanOptions));
  }


  public Set<String> scan(String pattern, int count) {

    if (StringUtils.isBlank(pattern)) {
      throw new GdsRedisException("pattern is blank");
    }

    if (count <= 0) {
      return Collections.emptySet();
    }

    ScanOptions scanOptions = ScanOptions.scanOptions().match(pattern).count(count).build();

    return convert2Set(redisTemplate.scan(scanOptions));
  }


  public void expire(RedisPrefix prefix, String key, long timeout) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (timeout <= 0) {
      return;
    }

    redisTemplate.expire(prefix.getRedisKey(key), timeout, TimeUnit.MILLISECONDS);
  }


  public void expireAt(RedisPrefix prefix, String key, Date date) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (System.currentTimeMillis() >= date.getTime()) {
      return;
    }

    redisTemplate.expireAt(prefix.getRedisKey(key), date);
  }
}
