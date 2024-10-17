package br.com.ccs.boot.server.handler;

import br.com.ccs.boot.server.annotations.Endpoint;
import br.com.ccs.boot.server.support.exceptions.HandlerNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.spi.Bean;
import jakarta.enterprise.inject.spi.BeanManager;
import jakarta.inject.Inject;
import org.slf4j.Logger;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class HandlerResolver {

    private final Logger log;
    private final Map<String, HandlerWrapper> controllerMap;

    @Inject
    public HandlerResolver(Logger log, BeanManager beanManager) {
        this.log = log;
        log.info("Initializing HandlerResolver...");

        var beans = beanManager.getBeans(Object.class);

        // Registra controladores com base na anotação @EndpointController
        var map = new HashMap<String, HandlerWrapper>();
        for (Bean<?> bean : beans) {
            Class<?> clazz = bean.getBeanClass();
            if (clazz.isAnnotationPresent(Endpoint.class)) {
                String path = clazz.getAnnotation(Endpoint.class).value();
                if (!path.startsWith("/")) {
                    path = "/".concat(path);
                }
                map.put(path, HandlerWrapper.of(beanManager.getReference(bean, clazz, beanManager.createCreationalContext(bean))));
            }
        }
        controllerMap = Collections.unmodifiableMap(map);
        log.info("HandlerResolver initialized with {} controllers.", controllerMap.size());
    }

    public HandlerWrapper resolve(URI uri) {
        var path = uri.getPath();

        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }

        var controller = controllerMap.get(path);

        if (controller == null) {
            throw new HandlerNotFoundException("No handler found for path: " + uri.getPath());
        }

        log.info("Request resolved to {}", controller.getClass());
        return controller;
    }
}

