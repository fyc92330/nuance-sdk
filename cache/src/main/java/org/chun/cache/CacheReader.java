package org.chun.cache;

public interface CacheReader<T> {

	T get(CacheKey key);

}
