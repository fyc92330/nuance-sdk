package org.chun.handler;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.chun.dto.HttpTimeoutProps;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class HttpTimeoutInterceptor implements Interceptor {


  @NotNull
  @Override
  public Response intercept(@NotNull Chain chain) throws IOException {

    Request request = chain.request();
    HttpTimeoutProps httpTimeoutProps = HttpTimeoutHolder.get();
    if (null == httpTimeoutProps) {
      return chain.proceed(request);
    }

    int connectTimeout = httpTimeoutProps.getConnectTimeout();
    int socketTimeout = httpTimeoutProps.getSocketTimeout();
    chain = chain.withConnectTimeout(connectTimeout, TimeUnit.SECONDS);
    chain = chain.withReadTimeout(socketTimeout, TimeUnit.SECONDS).withWriteTimeout(socketTimeout, TimeUnit.SECONDS);

    return chain.proceed(request);
  }
}
