package br.com.ccs.boot.server.support.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;

import java.text.SimpleDateFormat;

public class Jackson2JsonMapper {

    private final Logger log;

    @Inject
    public Jackson2JsonMapper(Logger log) {
        this.log = log;
    }

    @Produces
    @Singleton
    public ObjectMapper objectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a z"));
        log.info("Jackson ObjectMapper initialized");

        return objectMapper;
    }
}
