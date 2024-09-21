package br.com.ccs.boot.server.handler;

import br.com.ccs.boot.server.http.enums.HttpStatusCode;
import br.com.ccs.boot.server.support.exceptions.RequestBodyExtractException;
import br.com.ccs.boot.server.support.exceptions.ServerException;
import br.com.ccs.boot.server.support.exceptions.UnsupportedMethodException;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

@Singleton
public class HandlerDispatcher implements HttpHandler {

    private final Logger log;
    private final HandlerResolver resolver;
    private final ObjectMapper objectMapper;

    @Inject
    public HandlerDispatcher(HandlerResolver resolver, ObjectMapper objectMapper, Logger log) {
        this.resolver = resolver;
        this.objectMapper = objectMapper;
        this.log = log;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        log.info("Requested URI: {} ", exchange.getRequestURI());
        try {
            var handlerObject = resolver.resolve(exchange.getRequestURI());
            var method = methodResolver(handlerObject, exchange);
            var body = extractRequestBody(exchange);
            log.info("Request body: {} ", body);

            var returned = doInvokeMethod(method, body, handlerObject);

            if (returned == null) {
                exchange.sendResponseHeaders(HttpStatusCode.OK.getCode(), -1);
                exchange.close();
                return;
            }

            var response = objectMapper.writeValueAsString(returned);

            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(HttpStatusCode.OK.getCode(), response.getBytes().length);
            var os = exchange.getResponseBody();
            os.write(response.getBytes());
            exchange.close();

        } catch (Exception e) {
            sendError(exchange, e);
        }
    }

    @SuppressWarnings("unchecked")
    private Method methodResolver(Object handlerObject, HttpExchange exchange) {

        Method[] methods = handlerObject.getClass().getMethods();

        // Obtém a classe de anotação correspondente ao método HTTP
        Class<?> annotationType = EndpointMethodAnnotationMapper
                .resolveMethodAnotedType(exchange.getRequestMethod());

        /*
         Verifica se o método está anotado com a anotação correspondente ao método HTTP
         *Retorna o método que corresponde ao HTTP Method
        * Caso nenhum método seja encontrado lança uma exceção
         */
        return Arrays.stream(methods)
                .filter(m -> m.isAnnotationPresent((Class<? extends Annotation>) annotationType))
                .findFirst()
                .orElseThrow(() ->
                        new UnsupportedMethodException(exchange.getRequestMethod(), exchange.getRequestURI().getPath()));
    }

    private static String extractRequestBody(HttpExchange exchange) {
        try {
            return new String(exchange.getRequestBody().readAllBytes());
        } catch (IOException e) {
            throw new RequestBodyExtractException(e);
        }
    }

    private Object doInvokeMethod(Method method, String body, Object handlerObject) throws InvocationTargetException, IllegalAccessException {
        if (method.getParameters().length == 1) {
            var methodInputClass = method.getParameters()[0].getType();
            try {
                var input = objectMapper.readValue(body, methodInputClass);
                return method.invoke(handlerObject, input);
            } catch (JacksonException e) {
                throw new RequestBodyExtractException(e);
            }
        }

        return method.invoke(handlerObject);
    }

    private void sendError(HttpExchange exchange, Exception exception) throws IOException {
        log.error("Handler dispatcher error ", exception);
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