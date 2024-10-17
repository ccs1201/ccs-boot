package br.com.ccs.boot.server.handler;

import br.com.ccs.boot.server.annotations.Endpoint;
import br.com.ccs.boot.server.http.enums.HttpMethod;

import java.lang.reflect.Method;
import java.util.*;

public class HandlerWrapper {

    private final Object handler;
    private final Map<HttpMethod, Method> methodMap;

    private HandlerWrapper(Object handler) {
        this.handler = handler;
        this.methodMap = this.creatMethodMap();
    }

    private Map<HttpMethod, Method> creatMethodMap() {
        var methods = handler.getClass().getMethods();
        var mappedMethods = new HashMap<HttpMethod, Method>();

        Arrays.stream(methods).forEach(m -> {
            if (m.isAnnotationPresent(Endpoint.GET.class)) {
                mappedMethods.put(HttpMethod.GET, m);
                return;
            }

            if (m.isAnnotationPresent(Endpoint.PUT.class)) {
                mappedMethods.put(HttpMethod.PUT, m);
                return;
            }

            if (m.isAnnotationPresent(Endpoint.POST.class)) {
                mappedMethods.put(HttpMethod.POST, m);
                return;
            }

            if (m.isAnnotationPresent(Endpoint.DELETE.class)) {
                mappedMethods.put(HttpMethod.DELETE, m);
            }
        });
        return Collections.unmodifiableMap(mappedMethods);
    }

    public Object getHandler() {
        return handler;
    }

    public Optional<Method> getHttpMethodHandler(HttpMethod httpMethod) {
        return Optional.ofNullable(methodMap.get(httpMethod));
    }

    public static HandlerWrapper of(Object handler) {
        return new HandlerWrapper(handler);
    }
}
