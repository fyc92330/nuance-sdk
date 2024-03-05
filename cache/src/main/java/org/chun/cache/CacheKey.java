package org.chun.cache;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
public final class CacheKey implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

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


	@Override
	public int hashCode() {

		return this.key.hashCode();
	}

}
