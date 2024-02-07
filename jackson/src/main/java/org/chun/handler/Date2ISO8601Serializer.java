package org.chun.handler;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.Date;
import org.chun.helper.ThreadDateFormatter;

public class Date2ISO8601Serializer extends JsonSerializer<Date> {

  @Override
  public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

    jsonGenerator.writeString(ThreadDateFormatter.SDF.get().format(date));
  }
}
