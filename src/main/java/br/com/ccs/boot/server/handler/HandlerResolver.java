package br.com.ccs.boot.server.handler;

import br.com.ccs.boot.server.EndpointController;
import br.com.ccs.boot.server.annotations.Endpoint;
import br.com.ccs.boot.server.support.exceptions.HandlerNotFoundException;
import jakarta.enterprise.inject.Any;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.jboss.weld.inject.WeldInstance;
import org.slf4j.Logger;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class HandlerResolver {

    private final Logger log;
    private final Map<String, Object> controllerMap;

    @Inject
    public HandlerResolver(@Any WeldInstance<EndpointController> controllers, Logger log) {
        this.log = log;
        // Registra controladores com base na anotação @EndpointController
        var map = new HashMap<String, Object>();
        for (EndpointController controller : controllers) {
            Class<?> clazz = controller.getClass();
            if (clazz.isAnnotationPresent(Endpoint.class)) {
                String path = clazz.getAnnotation(Endpoint.class).value();
                if (!path.startsWith("/")) {
                    path = "/".concat(path);
                }
                map.put(path, controller);
            } else {
                log.warn("Controller {} is not annotated with @Endpoint", clazz.getName());
            }
        }
        controllerMap = Collections.unmodifiableMap(map);
        log.info("HandlerResolver initialized with {} controllers.", controllerMap.size());
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

        log.info("Request resolved to {}", controller.getClass());
        return controller;
    }
}

