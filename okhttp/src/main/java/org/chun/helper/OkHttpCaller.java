package org.chun.helper;

import lombok.RequiredArgsConstructor;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.chun.exception.GdsHttpException;
import org.chun.utils.JsonUtil;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class OkHttpCaller {

	private final OkHttpClient okHttpClient;


	public <T> T call(Request request, Class<T> clazz) {

		Call call = okHttpClient.newCall(request);
		try (Response response = call.execute()) {

			if (!response.isSuccessful()) {
				throw new GdsHttpException(response.message());
			}

			ResponseBody body = response.body();
			if (null == body) {
				return null;
			}

			return JsonUtil.HTTP.fromJson(body.string(), clazz);
		} catch (IOException e) {
			throw new GdsHttpException(e.getMessage());
		}
	}

}
