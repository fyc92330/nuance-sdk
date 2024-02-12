package org.chun.cache;

import java.util.concurrent.ConcurrentHashMap;

public abstract class DefaultCommonCache<T> implements CacheInitializer, CacheReader<T>, CacheEditor<T>, CacheScavenger, CacheKeyGenerator {

	private final ConcurrentHashMap<CacheKey, T> cache = new ConcurrentHashMap<>();

	public abstract void init();

	@Override
	public void update(CacheKey key, T param) {

		this.cache.put(key, param);
	}

	@Override
	public void delete(CacheKey key) {

		this.cache.remove(key);
	}

	@Override
	public CacheKey keygen(Object... param) {

		return CacheKey.of(param);
	}

	@Override
	public T get(CacheKey key) {

		return this.cache.get(key);
	}


}
