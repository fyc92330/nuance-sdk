package org.chun.cache;

import java.time.LocalDateTime;
import java.util.Map;

public interface CacheEditor<T> {

  void add(CacheKey key, T param);

  void addAll(Map<CacheKey, T> data);

  void refresh();

  void updatedTime();

  boolean isLatestTime(LocalDateTime now);
}
