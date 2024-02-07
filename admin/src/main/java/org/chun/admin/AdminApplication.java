package org.chun.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class AdminApplication {



  @PostMapping("/line/callback")
  public String lineCallBack(@RequestBody CallbackRequest request,
      @RequestHeader(name = "x-line-signature") String signature) {

    log.info("data:{}", request);
    log.info("signature:{}", signature);

    for (Event event : request.getEvents()) {
      if (event instanceof MessageEvent) {

        MessageContent content = ((MessageEvent<?>) event).getMessage();
        if (content instanceof TextMessageContent) {
          if ("閉嘴啦小GD".contains(((TextMessageContent) content).getText())) {
            String replyToken = ((MessageEvent<?>) event).getReplyToken();
            notificationService.replyMessage("幹", replyToken);
            System.exit(SpringApplication.exit(context));
          }
        }
      }
    }


    return "index";
  }


  public static void main(String[] args) {

    SpringApplication.run(AdminApplication.class, args);
  }


  @Bean
  public ApplicationListener<ApplicationReadyEvent> readyEventApplicationListener() {

    return event -> log.info("Admin Application Start, {}", event);
  }

}
