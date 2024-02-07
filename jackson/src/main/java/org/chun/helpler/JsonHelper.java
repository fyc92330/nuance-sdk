package org.chun.helpler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import org.chun.exception.GdsJsonException;
import org.chun.handler.LocalDateTime2LongSerializer;
import org.chun.handler.Long2LocalDateTimeDeserializer;

public class JsonHelper {


  private final ObjectMapper objectMapper;


  public JsonHelper(ObjectMapper objectMapper) {

    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    objectMapper.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
    objectMapper.enable(DeserializationFeature.USE_LONG_FOR_INTS);
    objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    objectMapper.registerModule(new JavaTimeModule());

    SimpleModule simpleModule = new SimpleModule();
    simpleModule.addSerializer(LocalDateTime.class, new LocalDateTime2LongSerializer());
    simpleModule.addDeserializer(LocalDateTime.class, new Long2LocalDateTimeDeserializer());
    objectMapper.registerModule(simpleModule);

    this.objectMapper = objectMapper;
  }


  public ObjectMapper getMapper() {

    return objectMapper;
  }


  public JavaType toJavaType(Type genericType) {

    return objectMapper.getTypeFactory().constructType(genericType);
  }


  public String toJson(Object object) {

    try {
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new GdsJsonException(e.getMessage(), e);
    }
  }


  public <T> T fromJson(String json, Class<T> clazz) {

    try {

      if (json == null) {
        return null;
      }

      return objectMapper.readValue(json, clazz);
    } catch (IOException e) {
      throw new GdsJsonException(e.getMessage(), e);
    }

  }


  public <T> T fromJson(String json, TypeReference<T> type) {

    try {

      if (json == null) {
        return null;
      }

      return objectMapper.readValue(json, type);
    } catch (IOException e) {
      throw new GdsJsonException(e.getMessage(), e);
    }
  }


  public <T> T fromJson(String json, JavaType type) {

    try {

      if (json == null) {
        return null;
      }

      return objectMapper.readValue(json, type);
    } catch (IOException e) {
      throw new GdsJsonException(e.getMessage(), e);
    }
  }


  public <T> T fromFile(File file, TypeReference<T> type) {

    try {

      if (file == null) {
        return null;
      }

      return objectMapper.readValue(file, type);
    } catch (IOException e) {
      throw new GdsJsonException(e.getMessage(), e);
    }
  }


  public void toJsonFile(String path, Object object) {

    try {

      objectMapper.writeValue(Paths.get(path).toFile(), object);
    } catch (IOException e) {
      throw new GdsJsonException(e.getMessage(), e);
    }
  }


  public <T> T convert(Object object, Class<T> clazz) {

    return objectMapper.convertValue(object, clazz);
  }


  public <T> T convert(Object object, JavaType type) {

    return objectMapper.convertValue(object, type);
  }


  public <T> T convert(Object object, TypeReference<T> typeReference) {

    return objectMapper.convertValue(object, typeReference);
  }


}
