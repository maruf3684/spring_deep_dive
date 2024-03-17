package org.cyber.universal_auth.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MapperUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static void print(Object object) {
        try {
            String json = objectMapper.writeValueAsString(object);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            System.err.println("Error converting object to JSON: " + e.getMessage());
        }
    }
}
