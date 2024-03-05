package org.chun.handler;


import org.chun.dto.HttpTimeoutProps;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HttpTimeoutHolder {

	private static final ThreadLocal<HttpTimeoutProps> TIMEOUT_HOLDER = ThreadLocal.withInitial(() -> null);

	private static final Map<String, HttpTimeoutProps> TIMEOUT_PROPS = new ConcurrentHashMap<>();


	public static void change(int connectTimeout, int socketTimeout) {

		String key = String.format("connect:%s_socket:%s", connectTimeout, socketTimeout);
		HttpTimeoutProps httpTimeoutProps = TIMEOUT_PROPS.computeIfAbsent(key, k -> {

			HttpTimeoutProps props = new HttpTimeoutProps();
			props.setConnectTimeout(connectTimeout);
			props.setSocketTimeout(socketTimeout);

			return props;
		});

		TIMEOUT_HOLDER.set(httpTimeoutProps);
	}


	public static HttpTimeoutProps get() {

		return TIMEOUT_HOLDER.get();
	}


	public static void clear() {

		TIMEOUT_HOLDER.remove();
	}

}
