package io.github.cscristianmoreno.config;

import com.fasterxml.jackson.databind.ObjectMapper;

public enum ObjectMapperConfig {
    INSTANCE;
    private ObjectMapper objectMapper = new ObjectMapper();

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
