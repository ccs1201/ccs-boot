package br.com.ccsboot.server.handler;

import br.com.ccsboot.server.EndpointController;
import br.com.ccsboot.server.annotations.Endpoint;
import br.com.ccsboot.server.support.exceptions.HandlerNotFoundException;
import jakarta.enterprise.inject.Any;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.jboss.weld.inject.WeldInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class HandlerResolver {

    private static final Logger logger = LoggerFactory.getLogger(HandlerResolver.class);
    private final Map<String, Object> controllerMap;

    @Inject
    public HandlerResolver(@Any WeldInstance<EndpointController> controllers) {
        // Registra controladores com base na anotação @EndpointController
        var map = new HashMap<String, Object>();
        for (Object controller : controllers) {
            Class<?> clazz = controller.getClass();
            if (clazz.isAnnotationPresent(Endpoint.class)) {
                String path = clazz.getAnnotation(Endpoint.class).value();
                if (!path.startsWith("/")) {
                    path = "/".concat(path);
                }
                map.put(path, controller);
            }
        }
        controllerMap = Collections.unmodifiableMap(map);
        logger.info("HandlerResolver initialized with {} controllers.", controllerMap.size());
    }

    public Object resolve(URI uri) {

        var path = uri.getPath();

        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }

        var controller = controllerMap.get(path);

        if (controller == null) {
            throw new HandlerNotFoundException("No handler found for path: " + uri.getPath());
        }
        logger.info("Request resolved to {}", controller.getClass());
        return controller;
    }
}

