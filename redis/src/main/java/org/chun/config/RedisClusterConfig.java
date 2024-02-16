package org.chun.config;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.chun.constant.RedisScript;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;

@RequiredArgsConstructor
@Configuration
public class RedisClusterConfig {


  private final LettuceConnectionFactory lettuceConnectionFactory;


  @Primary
  @Bean
  public RedisTemplate<String, String> redisTempLate() {

    // 但使用lua script會導致 KEY[1] 被當作value進行序列化
    // hashmap(key, item, value)形式的item會value的序列化導致lua script無法正確執行
    RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new StringRedisSerializer());
    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashValueSerializer(new StringRedisSerializer());
    redisTemplate.setConnectionFactory(lettuceConnectionFactory);

    return redisTemplate;
  }


  public static DefaultRedisScript<Long> load(String path) {

    DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
    redisScript.setResultType(Long.class);
    redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(path)));

    return redisScript;
  }


  @Bean
  public Map<RedisScript, DefaultRedisScript<Long>> redisScripts() {

    Map<RedisScript, DefaultRedisScript<Long>> scripts = new HashMap<>();
    scripts.put(RedisScript.UNLOCK, load(RedisScript.UNLOCK.getPath()));

    return scripts;
  }
}
