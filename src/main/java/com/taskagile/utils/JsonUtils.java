package com.taskagile.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtils {
    public static String toJson(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return  objectMapper.writeValueAsString(object);
        }catch (JsonProcessingException e){
            throw new RuntimeException("Failed to convert object to JSON string", e);
        }
    }
}
