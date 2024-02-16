package org.chun.hepler;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.chun.constant.RedisPrefix;
import org.chun.exception.GdsRedisException;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;


public class RedisZSet<T> extends RedisJsonParser<T> {

  private final RedisTemplate<String, String> redisTemplate;

  private final ZSetOperations<String, String> opsForZSet;


  public RedisZSet(RedisTemplate<String, String> redisTemplate, TypeReference<T> typeReference) {

    super(typeReference);
    this.redisTemplate = redisTemplate;
    this.opsForZSet = redisTemplate.opsForZSet();
  }


  public List<T> range(RedisPrefix prefix, String key, long start, long end) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    return convert(opsForZSet.range(prefix.getRedisKey(key), start, end));
  }


  public List<T> rangeByScore(RedisPrefix prefix, String key, double min, double max) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (min > max) {
      throw new GdsRedisException("min must <= max");
    }

    return convert(opsForZSet.rangeByScore(prefix.getRedisKey(key), min, max));
  }


  public List<T> rangeByScore(RedisPrefix prefix, String key, double min, double max, long offset, int count) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (offset < 0) {
      throw new GdsRedisException("offset must >= 0");
    }

    if (count <= 0) {
      throw new GdsRedisException("count must > 0");
    }

    return convert(opsForZSet.rangeByScore(prefix.getRedisKey(key), min, max, offset, count));
  }


  public Set<Map.Entry<T, Double>> rangeByScoreWithScores(RedisPrefix prefix, String key, double min, double max) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (min > max) {
      throw new GdsRedisException("min must <= max");
    }

    return convert2Entries(opsForZSet.rangeByScoreWithScores(prefix.getRedisKey(key), min, max));
  }


  public Set<Map.Entry<T, Double>> rangeByScoreWithScores(RedisPrefix prefix, String key, double min, double max, long offset, int count) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (min > max) {
      throw new GdsRedisException("min must <= max");
    }

    if (offset < 0) {
      throw new GdsRedisException("offset must >= 0");
    }

    if (count <= 0) {
      throw new GdsRedisException("count must > 0");
    }

    return convert2Entries(opsForZSet.rangeByScoreWithScores(prefix.getRedisKey(key), min, max, offset, count));
  }


  public List<T> reverseRangeByScore(RedisPrefix prefix, String key, double min, double max) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (min > max) {
      throw new GdsRedisException("min must <= max");
    }

    return convert(opsForZSet.reverseRangeByScore(prefix.getRedisKey(key), min, max));
  }


  public List<T> reverseRangeByScore(RedisPrefix prefix, String key, double min, double max, long offset, int count) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (offset < 0) {
      throw new GdsRedisException("offset must >= 0");
    }

    if (count <= 0) {
      throw new GdsRedisException("count must > 0");
    }

    return convert(opsForZSet.reverseRangeByScore(prefix.getRedisKey(key), min, max, offset, count));
  }


  public Set<Map.Entry<T, Double>> reverseRangeByScoreWithScores(RedisPrefix prefix, String key, double min, double max) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (min > max) {
      throw new GdsRedisException("min must <= max");
    }

    return convert2Entries(opsForZSet.reverseRangeByScoreWithScores(prefix.getRedisKey(key), min, max));
  }


  public Set<Map.Entry<T, Double>> reverseRangeByScoreWithScores(RedisPrefix prefix, String key, double min, double max, long offset, int count) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (min > max) {
      throw new GdsRedisException("min must <= max");
    }

    if (offset < 0) {
      throw new GdsRedisException("offset must >= 0");
    }

    if (count <= 0) {
      throw new GdsRedisException("count must > 0");
    }

    return convert2Entries(opsForZSet.reverseRangeByScoreWithScores(prefix.getRedisKey(key), min, max, offset, count));
  }


  public Set<Map.Entry<T, Double>> scan(RedisPrefix prefix, String key, int count) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (count <= 0) {
      return Collections.emptySet();
    }

    ScanOptions scanOptions = ScanOptions.scanOptions().count(count).build();

    return convert2Entries(opsForZSet.scan(prefix.getRedisKey(key), scanOptions));
  }


  public Double score(RedisPrefix prefix, String key, T value) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (null == value) {
      throw new GdsRedisException("value is blank");
    }

    return opsForZSet.score(prefix.getRedisKey(key), format(value));
  }


  public List<Double> score(RedisPrefix prefix, String key, T... values) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (ArrayUtils.isEmpty(values)) {
      return Collections.emptyList();
    }

    RedisCallback<Boolean> callback = (connection) -> {

      for (T value : values) {

        connection.zScore(prefix.getRedisKey(key).getBytes(), format(value).getBytes());
      }

      return null;
    };

    List<Object> results = redisTemplate.executePipelined(callback);
    if (results.isEmpty()) {
      return Collections.emptyList();
    }

    return convert(results);
  }


  public Map.Entry<T, Double> popMax(RedisPrefix prefix, String key) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    return convert2Entry(opsForZSet.popMax(prefix.getRedisKey(key)));
  }


  public Map.Entry<T, Double> popMax(RedisPrefix prefix, String key, long timeout) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (timeout <= 0) {
      throw new GdsRedisException("timeout must > 0");
    }

    return convert2Entry(opsForZSet.popMax(prefix.getRedisKey(key), timeout, TimeUnit.MILLISECONDS));
  }


  public Set<Map.Entry<T, Double>> popMax(RedisPrefix prefix, String key, int count) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (count <= 0) {
      return Collections.emptySet();
    }

    return convert2Entries(opsForZSet.popMax(prefix.getRedisKey(key), count));
  }


  public Map.Entry<T, Double> popMin(RedisPrefix prefix, String key) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    return convert2Entry(opsForZSet.popMin(prefix.getRedisKey(key)));
  }


  public Map.Entry<T, Double> popMin(RedisPrefix prefix, String key, long timeout) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (timeout <= 0) {
      throw new GdsRedisException("timeout must > 0");
    }

    return convert2Entry(opsForZSet.popMin(prefix.getRedisKey(key), timeout, TimeUnit.MILLISECONDS));
  }


  public Set<Map.Entry<T, Double>> popMin(RedisPrefix prefix, String key, int count) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (count <= 0) {
      return Collections.emptySet();
    }

    return convert2Entries(opsForZSet.popMin(prefix.getRedisKey(key), count));
  }


  public Boolean add(RedisPrefix prefix, String key, T value, Double score) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (null == score) {
      throw new GdsRedisException("score is blank");
    }

    return opsForZSet.add(prefix.getRedisKey(key), format(value), score);
  }


  public Long add(RedisPrefix prefix, String key, Map<T, Double> map) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (null == map || map.isEmpty()) {
      return 0L;
    }

    return opsForZSet.add(prefix.getRedisKey(key), format2SetTuple(map));
  }


  public Boolean addIfAbsent(RedisPrefix prefix, String key, T value, Double score) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (null == score) {
      throw new GdsRedisException("score is blank");
    }

    return opsForZSet.addIfAbsent(prefix.getRedisKey(key), format(value), score);
  }


  public Long addIfAbsent(RedisPrefix prefix, String key, Map<T, Double> map) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (null == map || map.isEmpty()) {
      return 0L;
    }

    return opsForZSet.addIfAbsent(prefix.getRedisKey(key), format2SetTuple(map));
  }


  public Double incrementScore(RedisPrefix prefix, String key, T value, double delta) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (null == value) {
      throw new GdsRedisException("value is blank");
    }

    return opsForZSet.incrementScore(prefix.getRedisKey(key), format(value), delta);
  }


  public Double decrementScore(RedisPrefix prefix, String key, T value, double delta) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (null == value) {
      throw new GdsRedisException("value is blank");
    }

    return opsForZSet.incrementScore(prefix.getRedisKey(key), format(value), -delta);
  }


  public Long rank(RedisPrefix prefix, String key, T value) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (null == value) {
      throw new GdsRedisException("value is blank");
    }

    return opsForZSet.rank(prefix.getRedisKey(key), format(value));
  }


  public Long reverseRank(RedisPrefix prefix, String key, T value) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (null == value) {
      throw new GdsRedisException("value is blank");
    }

    return opsForZSet.reverseRank(prefix.getRedisKey(key), format(value));
  }


  public Long count(RedisPrefix prefix, String key, double min, double max) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (min > max) {
      throw new GdsRedisException("min must <= max");
    }

    return opsForZSet.count(prefix.getRedisKey(key), min, max);
  }


  public Long size(RedisPrefix prefix, String key) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    return opsForZSet.size(prefix.getRedisKey(key));
  }


  public Long remove(RedisPrefix prefix, String key, T value) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (null == value) {
      throw new GdsRedisException("value is blank");
    }

    return opsForZSet.remove(prefix.getRedisKey(key), format(value));
  }


  public Long remove(RedisPrefix prefix, String key, T... values) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (ArrayUtils.isEmpty(values)) {
      return 0L;
    }

    RedisCallback<Boolean> callback = (connection) -> {

      for (T value : values) {

        connection.sRem(prefix.getRedisKey(key).getBytes(), format(value).getBytes());
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


  public Long removeRange(RedisPrefix prefix, String key, long start, long end) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    return opsForZSet.removeRange(prefix.getRedisKey(key), start, end);
  }


  public Long removeRangeByScore(RedisPrefix prefix, String key, double min, double max) {

    if (StringUtils.isBlank(key)) {
      throw new GdsRedisException("key is blank");
    }

    if (min > max) {
      throw new GdsRedisException("min must <= max");
    }

    return opsForZSet.removeRangeByScore(prefix.getRedisKey(key), min, max);
  }

}
