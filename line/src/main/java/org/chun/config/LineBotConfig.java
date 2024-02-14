package org.chun.config;


import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.LineOAuthClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LineBotConfig {

	@Value("${line.client.channel-access-token}")
	private String channelAccessToken;

	@Bean
	public LineMessagingClient messageClient() {

		return LineMessagingClient.builder(channelAccessToken).build();
	}

}
