package org.chun.handler;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.gw.util.DateTimeUtils;
import java.io.IOException;
import java.time.LocalDateTime;

public class ISO86012LocalDateTimeSerializer extends JsonDeserializer<LocalDateTime> {

  @Override
  public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {

    return DateTimeUtils.parse(jsonParser.getValueAsString());
  }
}
