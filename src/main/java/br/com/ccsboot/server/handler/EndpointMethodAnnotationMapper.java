package br.com.ccsboot.server.handler;

import br.com.ccsboot.server.annotations.Endpoint;
import br.com.ccsboot.server.http.enums.HttpMethod;
import br.com.ccsboot.server.support.exceptions.UnsupportedMethodException;

import java.util.Map;

public final class EndpointMethodAnnotationMapper {

    private EndpointMethodAnnotationMapper() {
    }

    private static final Map<String, Class<?>> HTTP_METHOD_MAP = Map.of(
            HttpMethod.GET.name(), Endpoint.GET.class,
            HttpMethod.POST.name(), Endpoint.POST.class,
            HttpMethod.PUT.name(), Endpoint.PUT.class,
            HttpMethod.DELETE.name(), Endpoint.DELETE.class
    );

    public static Class<?> resolveMethodAnotedType(String httpMethod) {
        if (httpMethod == null || httpMethod.isEmpty()) {
            throw new IllegalArgumentException("HTTP method cannot be null or empty");
        }

        Class<?> endpointAnnotation = HTTP_METHOD_MAP.get(httpMethod.toUpperCase());
        if (endpointAnnotation == null) {
            throw new UnsupportedMethodException(httpMethod);
        }

        return endpointAnnotation;
    }
}
