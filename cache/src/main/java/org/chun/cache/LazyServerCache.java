package org.chun.cache;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class LazyServerCache<T> implements CacheLazyInitializer, CacheReader<T>, CacheEditor<T>, CacheRemover, CacheKeyGenerator {

	private final ConcurrentHashMap<CacheKey, T> cache = new ConcurrentHashMap<>();

	private volatile LocalDateTime latestTime = LocalDateTime.now();


	@Override
	public void add(CacheKey key, T param) {

		this.cache.put(key, param);
	}

	@Override
	public void addAll(Map<CacheKey, T> data) {

		this.cache.putAll(data);
	}

	@Override
	public void refresh() {

		this.cache.clear();
	}

	@Override
	public void updatedTime() {

		this.latestTime = LocalDateTime.now();
	}

	@Override
	public boolean isLatestTime(LocalDateTime now) {

		return now.isAfter(this.latestTime);
	}

	@Override
	public void remove(CacheKey key) {

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
