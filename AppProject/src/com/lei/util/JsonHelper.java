package com.lei.util;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHelper
{
  public static <T> T jsonRead(byte[] src, Class<T> valueType)
  {
    if (src == null) {
      return null;
    }
    ObjectMapper mapper = new ObjectMapper();
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    try {
      return mapper.readValue(src, valueType);
    } catch (JsonMappingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static <T> T jsonRead(String src, Class<T> valueType, Class<T> type) {
    if (src == null) {
      return null;
    }
    ObjectMapper mapper = new ObjectMapper();
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    JavaType javaType = mapper.getTypeFactory().constructParametricType(valueType, new Class[] { type });
    try {
      return mapper.readValue(src, javaType);
    } catch (JsonMappingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static <T> T jsonRead(String src, Class<T> valueType) {
    if (src == null) {
      return null;
    }
    ObjectMapper mapper = new ObjectMapper();
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    try {
      return mapper.readValue(src, valueType);
    } catch (JsonMappingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static <T> T jsonRead(File src, Class<T> valueType)
  {
    if (src == null) {
      return null;
    }
    ObjectMapper mapper = new ObjectMapper();
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    try {
      return mapper.readValue(src, valueType);
    } catch (JsonMappingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static JsonNode jsonRead(byte[] src)
  {
    if (src == null) {
      return null;
    }
    ObjectMapper mapper = new ObjectMapper();
    try {
      return mapper.readTree(src);
    } catch (JsonMappingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static JsonNode jsonRead(String src) {
    if (src == null) {
      return null;
    }
    ObjectMapper mapper = new ObjectMapper();
    try {
      return mapper.readTree(src);
    } catch (JsonMappingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String serialize(Object value) {
    if (value == null) {
      return null;
    }
    ObjectMapper mapper = new ObjectMapper();
    try {
      return mapper.writeValueAsString(value);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }
}