package br.com.ccsboot.server.handler;

import br.com.ccsboot.server.annotations.Endpoint;
import br.com.ccsboot.server.exceptions.MetodoNaoSuportadoException;
import br.com.ccsboot.server.http.enums.HttpMethod;
import br.com.ccsboot.server.http.enums.HttpStatusCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.logging.Logger;

@Singleton
public class HanlderDispatcher implements HttpHandler {

    private static final Logger logger = Logger.getLogger(HanlderDispatcher.class.getName());

    @Inject
    private HandlerResolver resolver;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public HanlderDispatcher() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm a z"));
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.info("URI requisitada: " + exchange.getRequestURI());
        try {
            var handlerObject = resolver.resolve(exchange.getRequestURI());

            if (handlerObject == null) {
                sendError(exchange, HttpStatusCode.NOT_FOUND);
                return;
            }

            var method = methodResolver(handlerObject, exchange.getRequestMethod());
            var returned = method.invoke(handlerObject);

            if (returned == null) {
                exchange.sendResponseHeaders(HttpStatusCode.OK.getCode(), -1);
                exchange.close();
                return;
            }

            var response = objectMapper.writeValueAsString(returned);

            exchange.sendResponseHeaders(HttpStatusCode.OK.getCode(), response.getBytes().length);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            var os = exchange.getResponseBody();
            os.write(response.getBytes());
            exchange.close();


        } catch (IllegalAccessException | InvocationTargetException e) {
            sendError(exchange, HttpStatusCode.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            sendError(exchange, HttpStatusCode.BAD_REQUEST, e.getMessage());
        }
    }

    private Method methodResolver(Object handlerObject, String httpMethod) {
        Method[] methods = handlerObject.getClass().getMethods();

        // Obtém a classe da anotação correspondente ao método HTTP
        Class<?> annotationType = resolveMethodAnotedType(httpMethod);

        for (Method method : methods) {
            // Verifica se o método está anotado com a anotação correspondente ao método HTTP
            if (method.isAnnotationPresent((Class<? extends Annotation>) annotationType)) {
                return method;  // Retorna o método que corresponde ao HTTP Method
            }
        }

        // Caso nenhum método seja encontrado lança uma exceção
        throw new MetodoNaoSuportadoException("No method found for HTTP method: " + httpMethod);
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
            throw new MetodoNaoSuportadoException("HTTP method not supported: " + httpMethod);
        }

        return endpointAnnotation;
    }

    private static void sendError(HttpExchange exchange, HttpStatusCode statusCode) throws IOException {
        exchange.sendResponseHeaders(statusCode.getCode(), 0);
        exchange.close();
    }

    private static void sendError(HttpExchange exchange, HttpStatusCode statusCode, String body) throws IOException {
        exchange.sendResponseHeaders(statusCode.getCode(), body.getBytes().length);
        var os = exchange.getResponseBody();
        os.write(body.getBytes());
        exchange.close();
    }

}