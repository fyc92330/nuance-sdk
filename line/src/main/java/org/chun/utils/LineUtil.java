package org.chun.utils;

import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import java.util.ArrayList;
import java.util.List;

public class LineUtil {

  public static PushMessage pushMessage(String receipt, List<String> messages) {

    List<Message> lineMessages = new ArrayList<>();
    for (String message : messages) {
      TextMessage content = new TextMessage(message);
      lineMessages.add(content);
    }

    return new PushMessage(receipt, lineMessages);
  }
}
