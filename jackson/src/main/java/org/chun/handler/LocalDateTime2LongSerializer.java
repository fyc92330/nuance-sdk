package org.chun.handler;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.LocalDateTime;
import org.chun.utils.DateTimeUtils;

public class LocalDateTime2LongSerializer extends JsonSerializer<LocalDateTime> {

  @Override
  public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

    jsonGenerator.writeNumber(DateTimeUtils.toEpochMilli(localDateTime));
  }
}
