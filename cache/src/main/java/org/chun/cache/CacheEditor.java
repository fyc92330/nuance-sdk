package org.chun.cache;

public interface CacheEditor<T> {

	void update(CacheKey key, T param);

}
