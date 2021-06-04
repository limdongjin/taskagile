package com.taskagile.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public final class JsonUtils {
    public static String toJson(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return  objectMapper.writeValueAsString(object);
        }catch (JsonProcessingException e){
            throw new RuntimeException("Failed to convert object to JSON string", e);
        }
    }
    public static <T> T toObject(String json, Class<T> clazz) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            System.out.println("Failed to convert string " + json + " class " + clazz.getName() + " " + e);
            // [TODO] json utils logging
            //log.error("Failed to convert string `" + json + "` class `" + clazz.getName() + "`", e);
            return null;
        }
    }
}
