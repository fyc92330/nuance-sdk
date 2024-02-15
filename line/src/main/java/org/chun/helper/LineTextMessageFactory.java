package org.chun.helper;

import com.linecorp.bot.model.message.TextMessage;
import org.chun.dto.LineTextMessageContent;
import org.springframework.stereotype.Component;

@Component
public class LineTextMessageFactory implements LineMessageFactory<LineTextMessageContent, TextMessage> {

	@Override
	public TextMessage genMessage(LineTextMessageContent content) {

		return new TextMessage(content.getText());
	}

}
