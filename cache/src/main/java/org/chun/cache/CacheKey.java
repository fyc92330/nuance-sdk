package org.chun.cache;

public final class CacheKey {

	private final String key;

	CacheKey(String key) {

		this.key = key;
	}


	public static CacheKey of(Object... params) {

		String[] parts = new String[params.length];
		for (int i = 0; i < parts.length; i++) {
			parts[i] = String.valueOf(params[i]);
		}
		return new CacheKey(String.join("_", parts));
	}

	public String get() {

		return this.key;
	}

}
