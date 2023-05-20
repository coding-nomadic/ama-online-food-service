package com.order.service.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtils {
    JsonUtils() {

    }
    public static String toString(Object objectToConvert) throws IOException {
        return new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsString(objectToConvert);
    }

    public static <T> T convertWithClass(String jsonString, Class<T> type) throws IOException {
        return new ObjectMapper().readValue(jsonString, type);
    }
}