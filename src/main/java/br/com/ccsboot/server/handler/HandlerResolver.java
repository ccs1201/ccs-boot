package br.com.ccsboot.server.handler;

import br.com.ccsboot.annotations.EndpointController;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Singleton
public class HanlderResolver {

    private static final Logger logger = Logger.getLogger(HanlderResolver.class.getName());
    private final Map<String, Object> controllerMap = new HashMap<>();

    @Inject
    public HanlderResolver(Instance<EndpointController> controllers) {
        // Registra controladores com base na anotação @EndpointController
        for (Object controller : controllers) {
            Class<?> clazz = controller.getClass();
            if (clazz.isAnnotationPresent(EndpointController.class)) {
                EndpointController annotation = clazz.getAnnotation(EndpointController.class);
                String path = annotation.value();
                controllerMap.put(path, controller);
            }
        }

        logger.info("HandlerResolver inicializado com " + controllerMap.size() + " controladores.");
    }

    public Object resolve(String path) {
        return controllerMap.get(path);
    }
}

