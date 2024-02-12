package org.chun.admin;

import com.linecorp.bot.model.event.CallbackRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chun.helper.LineClient;
import org.chun.util.JsonUtils;
import org.chun.utils.LineUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@ComponentScan("org.chun.config")
@SpringBootApplication
public class AdminApplication {

  private LineClient lineClient;

  @PostMapping("/line/callback")
  public ResponseEntity<?> lineCallBack(@RequestBody CallbackRequest request,
      @RequestHeader(name = "x-line-signature") String signature) {

    log.info("data:{}", request);
    log.info("signature:{}", signature);

    return ResponseEntity.ok().build();
  }


  public static void main(String[] args) {

    SpringApplication.run(AdminApplication.class, args);
  }


  @Bean
  public ApplicationListener<ApplicationReadyEvent> readyEventApplicationListener() {

    return event -> log.info("Admin Application Start, {}", JsonUtils.SYSTEM.toJson(event));
  }

}
