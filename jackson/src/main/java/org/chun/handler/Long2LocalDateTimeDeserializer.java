package org.chun.handler;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.LocalDateTime;
import org.chun.utils.DateTimeUtils;

public class Long2LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

  @Override
  public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

    return DateTimeUtils.ofInstant(jsonParser.getValueAsLong());
  }
}
