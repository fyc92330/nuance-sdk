package org.chun.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.time.LocalDateTime;
import java.util.Date;
import org.chun.handler.Date2ISO8601Serializer;
import org.chun.handler.ISO86012DateSerializer;
import org.chun.handler.ISO86012LocalDateTimeSerializer;
import org.chun.handler.LocalDateTime2ISO8601Serializer;
import org.chun.helper.JsonHelper;

public class JsonUtil {

  public static final JsonHelper SYSTEM = new JsonHelper(new ObjectMapper());

  public static final JsonHelper REDIS = new JsonHelper(new ObjectMapper());

  static {

    SimpleModule simpleModule = new SimpleModule();
    simpleModule.addSerializer(Date.class, new Date2ISO8601Serializer());
    simpleModule.addDeserializer(Date.class, new ISO86012DateSerializer());
    simpleModule.addSerializer(LocalDateTime.class, new LocalDateTime2ISO8601Serializer());
    simpleModule.addDeserializer(LocalDateTime.class, new ISO86012LocalDateTimeSerializer());

    ObjectMapper systemMapper = SYSTEM.getMapper();
    systemMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    systemMapper.registerModule(simpleModule);

    ObjectMapper redisMapper = REDIS.getMapper();
    redisMapper.setAnnotationIntrospector(new JsonIgnoreDisabler());
  }


  private static class JsonIgnoreDisabler extends JacksonAnnotationIntrospector {

    @Override
    public boolean hasIgnoreMarker(AnnotatedMember m) {

      return false;
    }
  }
}
