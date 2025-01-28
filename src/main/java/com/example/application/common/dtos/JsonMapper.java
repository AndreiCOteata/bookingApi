package com.example.application.common.dtos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class JsonMapper {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonMapper(){}

    public static <T> T unmarshall(String body, Class<T> obj) throws IOException{
        return OBJECT_MAPPER.readValue(body, obj);
    }

    public static <T> T unmarshall(String body, TypeReference<T> reference) throws IOException{
        return OBJECT_MAPPER.readValue(body, reference);
    }

    public static String marshall(Object obj) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(obj);
    }

    public static String writeValueAsString(Object value) throws IOException {
        return OBJECT_MAPPER.writeValueAsString(value);
    }
}
