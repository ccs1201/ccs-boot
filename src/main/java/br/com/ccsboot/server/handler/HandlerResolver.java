package br.com.ccsboot.server.handler;

import br.com.ccsboot.server.annotations.Endpoint;
import br.com.ccsboot.server.EndpointController;
import br.com.ccsboot.server.exceptions.HandlerNotFoundException;
import jakarta.enterprise.inject.Any;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.jboss.weld.inject.WeldInstance;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Singleton
public class HandlerResolver {

    private static final Logger logger = Logger.getLogger(HandlerResolver.class.getName());
    private final Map<String, Object> controllerMap = new HashMap<>();

    @Inject
    public HandlerResolver(@Any WeldInstance<EndpointController> controllers) {
        // Registra controladores com base na anotação @EndpointController
        for (Object controller : controllers) {
            Class<?> clazz = controller.getClass();
            if (clazz.isAnnotationPresent(Endpoint.class)) {
                Endpoint annotation = clazz.getAnnotation(Endpoint.class);
                String path = annotation.value();
                if (!path.startsWith("/")) {
                    path = "/".concat(path);
                }
                controllerMap.put(path, controller);
            }
        }

        logger.info("HandlerResolver initialized with " + controllerMap.size() + " controllers.");
    }

    public Object resolve(URI uri) {
        var controller = controllerMap.get(uri.getPath());

        if (controller == null) {
            throw new HandlerNotFoundException("No controller found for path: " + uri.getPath());
        }
        logger.info("Request send to controller: " + controller.getClass().getSimpleName());
        return controller;
    }
}

