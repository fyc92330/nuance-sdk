package org.chun.hepler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.chun.constant.RedisCommonKey;
import org.chun.constant.RedisScript;
import org.chun.exception.GdsRedisException;
import org.chun.utils.LogUtil;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.types.Expiration;


public class RedisLock {

  private static final ThreadLocal<Map<String, String>> REDIS_LOCKS = ThreadLocal.withInitial(HashMap::new);

  private final RedisTemplate<String, String> redisTemplate;

  private final Map<RedisScript, DefaultRedisScript<Long>> redisScripts;


  public RedisLock(RedisTemplate<String, String> redisTemplate, Map<RedisScript, DefaultRedisScript<Long>> redisScripts) {

    this.redisTemplate = redisTemplate;
    this.redisScripts = redisScripts;
  }


  public boolean tryLock(String key, long timeout, int retries, long interval) {

    if (timeout <= 0) {
      throw new GdsRedisException("timeout must > 0");
    }

    if (retries < 0) {
      throw new GdsRedisException("retries must >= 0");
    }

    if (interval < 0) {
      throw new GdsRedisException("interval must >= 0");
    }

    for (int i = 0; i <= retries; i++) {

      try {

        boolean success = tryLock(key, timeout);
        if (success) {
          return true;
        }

        Thread.sleep(interval);
      } catch (Exception e) {
        LogUtil.REDIS.error("tryLock error:{} key = {} ", e.getMessage(), key, e);
      }
    }

    return false;
  }


  public boolean tryLock(String key, long timeout) {

    if (timeout <= 0) {
      throw new GdsRedisException("timeout must > 0");
    }

    String redisKey = RedisCommonKey.LOCK.getRedisKey(key);
    Map<String, String> locks = REDIS_LOCKS.get();
    if (locks.containsKey(redisKey)) {
      return false;
    }

    try {

      String uuid = UUID.randomUUID().toString();
      RedisCallback<Boolean> callback = (connection) -> {

        locks.put(redisKey, uuid);
        return connection.set(redisKey.getBytes(), uuid.getBytes(), Expiration.milliseconds(timeout),
            RedisStringCommands.SetOption.SET_IF_ABSENT);
      };

      Boolean success = redisTemplate.execute(callback);
      if (null == success || !success) {

        locks.remove(redisKey);
        return false;
      }

      return true;
    } catch (Exception e) {

      LogUtil.REDIS.error("tryLock error:{} key = {} ", e.getMessage(), key, e);
      unlock(key);
      throw new GdsRedisException(e.getMessage());
    }
  }


  public void unlock(String key) {

    String redisKey = RedisCommonKey.LOCK.getRedisKey(key);
    String uuid = REDIS_LOCKS.get().get(redisKey);
    if (StringUtils.isBlank(uuid)) {
      return;
    }

    try {

      DefaultRedisScript<Long> redisScript = redisScripts.get(RedisScript.UNLOCK);
      redisTemplate.execute(redisScript, Collections.singletonList(redisKey), uuid);
    } catch (Exception e) {
      throw new GdsRedisException(e.getMessage());
    } finally {
      REDIS_LOCKS.get().remove(redisKey);
    }
  }
}
