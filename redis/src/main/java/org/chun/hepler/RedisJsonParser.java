package org.chun.hepler;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.keyvalue.DefaultMapEntry;
import org.apache.commons.lang3.ArrayUtils;
import org.chun.constant.RedisPrefix;
import org.chun.utils.JsonUtil;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ZSetOperations;

public abstract class RedisJsonParser<T> {

  private final TypeReference<T> typeReference;


  public RedisJsonParser(TypeReference<T> typeReference) {

    this.typeReference = typeReference;
  }


  protected String format(Object object) {

    if (null == object) {
      return null;
    }

    if (object instanceof String) {
      return (String) object;
    }

    return JsonUtil.REDIS.toJson(object);
  }


  protected List<String> format2List(RedisPrefix prefix, Collection<String> keys) {

    if (null == keys || keys.isEmpty()) {
      return Collections.emptyList();
    }

    List<String> temps = new ArrayList<>();
    for (String key : keys) {

      temps.add(prefix.getRedisKey(key));
    }

    return temps;
  }


  protected List<String> format2List(Collection<T> elements) {

    if (CollectionUtils.isEmpty(elements)) {
      return Collections.emptyList();
    }

    List<String> temps = new ArrayList<>();
    for (T element : elements) {

      temps.add(format(element));
    }

    return temps;
  }


  protected List<String> format2List(T... elements) {

    if (ArrayUtils.isEmpty(elements)) {
      return Collections.emptyList();
    }

    List<String> temps = new ArrayList<>();
    for (T element : elements) {

      temps.add(format(element));
    }

    return temps;
  }


  protected Map<String, String> format2Map(Map<String, T> map) {

    if (null == map || map.isEmpty()) {
      return Collections.emptyMap();
    }

    Map<String, String> temps = new HashMap<>();
    for (Map.Entry<String, T> entry : map.entrySet()) {

      temps.put(entry.getKey(), format(entry.getValue()));
    }

    return temps;
  }


  protected Map<String, String> format2Map(RedisPrefix prefix, Map<String, T> map) {

    if (null == map || map.isEmpty()) {
      return Collections.emptyMap();
    }

    Map<String, String> temps = new HashMap<>();
    for (Map.Entry<String, T> entry : map.entrySet()) {

      temps.put(prefix.getRedisKey(entry.getKey()), format(entry.getValue()));
    }

    return temps;
  }


  protected Set<ZSetOperations.TypedTuple<String>> format2SetTuple(Map<T, Double> map) {

    if (null == map || map.isEmpty()) {
      return Collections.emptySet();
    }

    Set<ZSetOperations.TypedTuple<String>> temps = new HashSet<>();
    for (Map.Entry<T, Double> entry : map.entrySet()) {

      temps.add(ZSetOperations.TypedTuple.of(format(entry.getKey()), entry.getValue()));
    }

    return temps;
  }


  protected T convert(String object) {

    return JsonUtil.REDIS.fromJson(object, typeReference);
  }


  protected List<T> convert(Collection<String> objects) {

    if (CollectionUtils.isEmpty(objects)) {
      return Collections.emptyList();
    }

    List<T> temp = new ArrayList<>();
    for (String str : objects) {

      temp.add(convert(str));
    }

    return temp;
  }


  protected Map<String, T> convert(Map<String, String> objects) {

    if (MapUtils.isEmpty(objects)) {
      return Collections.emptyMap();
    }

    Map<String, T> temp = new LinkedHashMap<>();
    for (Map.Entry<String, String> entry : objects.entrySet()) {

      temp.put(entry.getKey(), convert(entry.getValue()));
    }

    return temp;
  }


  protected <V> List<V> convert(List<Object> objects) {

    return JsonUtil.REDIS.convert(objects, new TypeReference<List<V>>() {

    });
  }


  protected Map.Entry<T, Double> convert2Entry(ZSetOperations.TypedTuple<String> tuple) {

    if (null == tuple) {
      return null;
    }

    return new DefaultMapEntry<>(convert(tuple.getValue()), tuple.getScore());
  }


  protected Set<T> convert2Set(Cursor<String> cursor) {

    if (null == cursor) {
      return Collections.emptySet();
    }

    try {

      Set<T> temps = new LinkedHashSet<>();
      while (cursor.hasNext()) {

        String temp = cursor.next();
        temps.add(convert(temp));
      }

      return temps;
    } finally {
      cursor.close();
    }
  }


  protected Map<String, T> convert2Map(Cursor<Map.Entry<String, String>> cursor) {

    if (null == cursor) {
      return Collections.emptyMap();
    }

    try {

      Map<String, T> temps = new LinkedHashMap<>();
      while (cursor.hasNext()) {

        Map.Entry<String, String> entry = cursor.next();
        temps.put(entry.getKey(), convert(entry.getValue()));
      }

      return temps;
    } finally {
      cursor.close();
    }
  }


  protected Set<Map.Entry<T, Double>> convert2Entries(Set<ZSetOperations.TypedTuple<String>> tuples) {

    if (CollectionUtils.isEmpty(tuples)) {
      return Collections.emptySet();
    }

    Set<Map.Entry<T, Double>> temps = new LinkedHashSet<>();
    for (ZSetOperations.TypedTuple<String> tuple : tuples) {

      temps.add(new DefaultMapEntry<>(convert(tuple.getValue()), tuple.getScore()));
    }

    return temps;
  }


  protected Set<Map.Entry<T, Double>> convert2Entries(Cursor<ZSetOperations.TypedTuple<String>> cursor) {

    if (null == cursor) {
      return Collections.emptySet();
    }

    try {

      Set<Map.Entry<T, Double>> temps = new LinkedHashSet<>();
      while (cursor.hasNext()) {

        ZSetOperations.TypedTuple<String> tuple = cursor.next();
        temps.add(new DefaultMapEntry<>(convert(tuple.getValue()), tuple.getScore()));
      }

      return temps;
    } finally {
      cursor.close();
    }
  }
}
