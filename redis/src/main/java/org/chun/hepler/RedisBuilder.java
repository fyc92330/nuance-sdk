package org.chun.hepler;

import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.PostConstruct;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.chun.constant.RedisScript;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RedisBuilder {

  private final RedisTemplate<String, String> redisTemplate;

  private final Map<RedisScript, DefaultRedisScript<Long>> redisScripts;

  private RedisOps redisOps;

  private RedisLock redisLock;


  @PostConstruct
  public void init() {

    this.redisOps = new RedisOps(redisTemplate);
    this.redisLock = new RedisLock(redisTemplate, redisScripts);
  }


  public RedisOps ops() {

    return redisOps;
  }


  public RedisLock lock() {

    return redisLock;
  }


  public <T> RedisValue<T> value(TypeReference<T> typeReference) {


    return new RedisValue<>(redisTemplate, typeReference);
  }


  public <T> RedisHash<T> hash(TypeReference<T> typeReference) {

    return new RedisHash<>(redisTemplate, typeReference);
  }


  public <T> RedisList<T> list(TypeReference<T> typeReference) {

    return new RedisList<>(redisTemplate, typeReference);
  }


  public <T> RedisSet<T> set(TypeReference<T> typeReference) {

    return new RedisSet<>(redisTemplate, typeReference);
  }


  public <T> RedisZSet<T> zset(TypeReference<T> typeReference) {

    return new RedisZSet<>(redisTemplate, typeReference);
  }
}
