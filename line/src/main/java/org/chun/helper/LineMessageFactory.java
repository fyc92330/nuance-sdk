package org.chun.helper;

import com.linecorp.bot.model.message.Message;
import org.chun.dto.LineMessageContent;

public interface LineMessageFactory<T extends LineMessageContent, R extends Message> {

	R genMessage(T content);

}
