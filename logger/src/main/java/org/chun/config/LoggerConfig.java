package org.chun.config;

import org.chun.handler.ElkLogger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerConfig implements InitializingBean {

  @Value("${app-id}")
  private String appId;


  @Override
  public void afterPropertiesSet() throws Exception {

    ElkLogger.APP_ID = appId;
  }
}
