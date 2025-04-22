package br.com.ccs.boot.support.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;

public class Jackson2JsonMapper {

    private final Logger log;

    @Inject
    public Jackson2JsonMapper(Logger log) {
        this.log = log;
    }

    @Produces
    @Singleton
    public ObjectMapper objectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .registerModule(new JavaTimeModule());
//                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a z"));
        log.info("Jackson ObjectMapper initialized");

        return objectMapper;
    }
}
