package org.chun.handler;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.chun.exception.GDJsonException;
import org.chun.helper.ThreadDateFormatter;

public class ISO86012DateSerializer extends JsonDeserializer<Date> {

  @Override
  public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {

    try {

      return ThreadDateFormatter.SDF.get().parse(jsonParser.getValueAsString());
    } catch (ParseException e) {
      throw new GDJsonException(e);
    }
  }
}
