package gw.config;

import com.gw.util.LogUtils;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class OkHttpConnectionPoolConfig {

  // The maximum number of idle connections for each address
  @Value("${okhttp.max-idle-connections}")
  private int maxIdleConnections;

  // Time in seconds to keep the connection alive in the pool before closing it
  @Value("${okhttp.keep-alive-duration}")
  private long keepAliveDuration;

  // The maximum number of requests to execute concurrently
  @Value("${okhttp.max-requests}")
  private int maxRequests;

  // The maximum number of requests for each host to execute concurrently
  @Value("${okhttp.max-requests-per-host}")
  private int maxRequestsPerHost;


  @Primary
  @Bean
  public ConnectionPool connectionPool() {

    return new ConnectionPool(maxIdleConnections, keepAliveDuration, TimeUnit.SECONDS);
  }


  @Primary
  @Bean
  public Dispatcher dispatcher() {

    ExecutorService executorService = new ThreadPoolExecutor(maxIdleConnections, maxRequests, keepAliveDuration, TimeUnit.SECONDS, new SynchronousQueue<>(),
        (runnable, executor) -> {

          if (!executor.isShutdown()) {

            LogUtils.HTTP.warn("all threads are busy currently in the http thread pool");
            runnable.run();
          }
        });

    Dispatcher dispatcher = new Dispatcher(executorService);
    dispatcher.setMaxRequests(maxRequests);
    dispatcher.setMaxRequestsPerHost(maxRequestsPerHost);

    return dispatcher;
  }

}
