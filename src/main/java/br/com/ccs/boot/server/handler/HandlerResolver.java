package br.com.ccs.boot.server.handler;

import br.com.ccs.boot.annotations.Endpoint;
import br.com.ccs.boot.server.handler.wrapper.HandlerWrapper;
import br.com.ccs.boot.support.exceptions.HandlerNotFoundException;
import jakarta.enterprise.inject.spi.BeanManager;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class HandlerResolver {

    private final Logger log;
    private final Map<String, HandlerWrapper> handlerMap;

    @Inject
    public HandlerResolver(Logger log, BeanManager beanManager) {
        this.log = log;
        log.info("Initializing HandlerResolver...");

        var beans = beanManager.getBeans(Object.class);

        // Registra controladores com base na anotação @EndpointController
        var map = new HashMap<String, HandlerWrapper>();

        beans.stream()
                .filter(bean -> bean.getBeanClass().isAnnotationPresent(Endpoint.class))
                .forEach(bean -> {
                    Class<?> clazz = bean.getBeanClass();
                    Endpoint endpoint = clazz.getAnnotation(Endpoint.class);
                    String path = endpoint.value().startsWith("/") ? endpoint.value() : "/" + endpoint.value();
                    map.put(path, HandlerWrapper.of(clazz, beanManager.getReference(bean, clazz,
                            beanManager.createCreationalContext(bean))));
                });
        handlerMap = Collections.unmodifiableMap(map);
        log.info("HandlerResolver initialized with {} controllers.", handlerMap.size());
    }

    public HandlerWrapper resolve(URI uri) {
        var path = uri.getPath();

        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }

        var handlerWrapper = handlerMap.get(path);

        if (handlerWrapper == null) {
            throw new HandlerNotFoundException("No handler found for path: " + uri.getPath());
        }

        log.info("Request resolved to {}", handlerWrapper.getHandlerClass());
        return handlerWrapper;
    }
}

