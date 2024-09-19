package br.com.ccsboot.server.handler;

import br.com.ccsboot.server.http.enums.HttpStatusCode;
import br.com.ccsboot.server.support.exceptions.ServerException;
import br.com.ccsboot.server.support.exceptions.UnsupportedMethodException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;

@Singleton
public class HandlerDispatcher implements HttpHandler {

    private static final Logger logger = LoggerFactory.getLogger(HandlerDispatcher.class);
    private HandlerResolver resolver;
    private ObjectMapper objectMapper;

    @Inject
    public HandlerDispatcher(HandlerResolver resolver, ObjectMapper objectMapper) {
        this.resolver = resolver;
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.info("Requested URI: {} ", exchange.getRequestURI());
        try {
            var handlerObject = resolver.resolve(exchange.getRequestURI());

            if (handlerObject == null) {
                sendError(exchange, HttpStatusCode.NOT_FOUND);
                return;
            }

            var method = methodResolver(handlerObject, exchange);
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
            sendError(exchange, e);
        }
    }

    private Method methodResolver(Object handlerObject, HttpExchange exchange) {
        Method[] methods = handlerObject.getClass().getMethods();

        // Obtém a classe da anotação correspondente ao método HTTP
        Class<?> annotationType = HandlerMethodResolver.resolveMethodAnotedType(exchange.getRequestMethod());

        for (Method method : methods) {
            // Verifica se o método está anotado com a anotação correspondente ao método HTTP
            if (method.isAnnotationPresent((Class<? extends Annotation>) annotationType)) {
                return method;  // Retorna o método que corresponde ao HTTP Method
            }
        }

        // Caso nenhum método seja encontrado lança uma exceção
        throw new UnsupportedMethodException(
                MessageFormat.format("No handler found for HTTP method: {0} in path {1}",
                        exchange.getRequestMethod(),
                        exchange.getRequestURI().getPath()));
    }

    private static void sendError(HttpExchange exchange, HttpStatusCode statusCode) throws IOException {
        exchange.sendResponseHeaders(statusCode.getCode(), 0);
        exchange.close();
    }

    private static void sendError(HttpExchange exchange, Exception exception) throws IOException {

        logger.error("Handler dispatcher error ", exception);

        var code = HttpStatusCode.INTERNAL_SERVER_ERROR.getCode();

        if (exception instanceof ServerException e) {
            code = e.getStatusCode().getCode();
        }

        exchange.sendResponseHeaders(code, exception.getMessage().getBytes().length);
        var os = exchange.getResponseBody();
        os.write(exception.getMessage().getBytes());
        exchange.close();
    }

}