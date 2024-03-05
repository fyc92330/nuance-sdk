package org.chun.config;

import lombok.RequiredArgsConstructor;
import okhttp3.ConnectionPool;
import okhttp3.CookieJar;
import okhttp3.Dispatcher;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import org.chun.handler.HttpTimeoutInterceptor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Configuration
public class OkHttpClientConfig implements DisposableBean {

	// The timeout for establish a connection(in seconds)
	@Value("${okhttp.connect-timeout}")
	private long connectTimeout;

	// The maximum period of inactivity between two data packets(in seconds)
	@Value("${okhttp.socket-timeout}")
	private long socketTimeout;

	private final ConnectionPool connectionPool;

	private final Dispatcher dispatcher;

	private final HttpTimeoutInterceptor httpTimeoutInterceptor;


	@Bean
	public OkHttpClient okHttpClient() throws Exception {

		SSLContext sslContext = SSLContext.getInstance("SSL");
		X509TrustManager x509TrustManager = x509TrustManager();
		sslContext.init(null, new TrustManager[]{x509TrustManager}, SecureRandom.getInstance("SHA1PRNG"));
		SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

		CookieJar cookieJar = new JavaNetCookieJar(new CookieManager(null, CookiePolicy.ACCEPT_ALL));

		return new OkHttpClient().newBuilder()
				.sslSocketFactory(sslSocketFactory, x509TrustManager)
//				        .connectionPool(connectionPool)
				.connectTimeout(connectTimeout, TimeUnit.SECONDS)
				.readTimeout(socketTimeout, TimeUnit.SECONDS)
				.writeTimeout(socketTimeout, TimeUnit.SECONDS)
				//        .dispatcher(dispatcher)
				//        .hostnameVerifier((hostname, session) -> true)
				//        .cookieJar(cookieJar)
//				        .retryOnConnectionFailure(false)
				.addInterceptor(httpTimeoutInterceptor)
				.build();
	}


	@Override
	public void destroy() throws Exception {

		connectionPool.evictAll();
		dispatcher.cancelAll();
	}


	private X509TrustManager x509TrustManager() {

		return new X509TrustManager() {

			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) {

			}


			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) {

			}


			@Override
			public X509Certificate[] getAcceptedIssuers() {

				return new X509Certificate[0];
			}
		};
	}


}
