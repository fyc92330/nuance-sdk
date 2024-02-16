package org.chun.config;

import org.chun.helper.LoggerHelper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerConfig implements InitializingBean {

  @Value("${app-source-id}")
  private String appSourceId;


  @Override
  public void afterPropertiesSet() throws Exception {

    LoggerHelper.APP_SOURCE_ID = appSourceId;
  }
}
