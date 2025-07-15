package br.com.ccs.boot.config;

import br.com.ccs.boot.server.ServerLauncher;
import br.com.ccs.boot.server.handler.HandlerDispatcher;
import br.com.ccs.boot.server.handler.HandlerResolver;
import br.com.ccs.boot.server.http.SimpleHttpServer;
import br.com.ccs.boot.support.json.converter.ContentConverter;
import br.com.ccs.boot.support.json.converter.impl.JsonContentConverterImpl;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.BeanManager;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;

@ApplicationScoped
public class CcsBootConfig {

    @Produces
    public Logger produceLogger(InjectionPoint injectionPoint) {
        return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass());
    }

    @Produces
    @Singleton
    public ObjectMapper produceObjectMapper() {
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .registerModule(new JavaTimeModule())
                .setDateFormat(DateFormat.getDateTimeInstance());
    }

    @Produces
    @Singleton
    public ContentConverter produceContentConverter(ObjectMapper objectMapper) {
        return new JsonContentConverterImpl(objectMapper);
    }

    @Produces
    @Singleton
    public HandlerResolver produceHandlerResolver(Logger logger, BeanManager beanManager) {
        return new HandlerResolver(logger, beanManager);
    }

    @Produces
    @Singleton
    public HandlerDispatcher produceHandlerDispatcher(HandlerResolver handlerResolver, ContentConverter contentConverter, Logger logger) {
        return new HandlerDispatcher(handlerResolver, contentConverter, logger);
    }

    @Produces
    @Singleton
    public ServerLauncher produceServerLauncher(SimpleHttpServer simpleHttpServer) {
        return new ServerLauncher(simpleHttpServer);
    }

    @Produces
    @Singleton
    public SimpleHttpServer produceSimpleHttpServer(HandlerDispatcher handlerDispatcher) {
        return new SimpleHttpServer(handlerDispatcher);
    }
}