package org.chun.cache;

public interface CacheKeyGenerator {

	CacheKey keygen(Object... param);
}
