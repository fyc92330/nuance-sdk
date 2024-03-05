package org.chun.cache;

import java.util.Map;

public interface CacheReader<T> {

	T get(CacheKey key);

	Map<CacheKey, T> getAll();

}
