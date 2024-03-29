package org.chun.service;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import java.util.Collections;

import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.message.TemplateMessage;
import lombok.RequiredArgsConstructor;
import org.chun.utils.LineUtil;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LineClient {

  private final LineMessagingClient messagingClient;

  public void send(String receipt, String message) {

    PushMessage pushMessage = LineUtil.pushMessage(receipt, Collections.singletonList(message));
    messagingClient.pushMessage(pushMessage);
  }
}
