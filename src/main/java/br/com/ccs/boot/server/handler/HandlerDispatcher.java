package br.com.ccs.boot.server.handler;

import br.com.ccs.boot.annotations.EndpointResponseCode;
import br.com.ccs.boot.server.handler.wrapper.HandlerWrapper;
import br.com.ccs.boot.server.http.enums.HttpMethod;
import br.com.ccs.boot.server.http.enums.HttpStatusCode;
import br.com.ccs.boot.support.exceptions.HandlerException;
import br.com.ccs.boot.support.exceptions.RequestBodyExtractException;
import br.com.ccs.boot.support.exceptions.ServerException;
import br.com.ccs.boot.support.exceptions.UnsupportedMethodException;
import br.com.ccs.boot.support.json.converter.ContentConverter;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

@ApplicationScoped
public class HandlerDispatcher implements HttpHandler {

    private final Logger log;
    private final HandlerResolver resolver;
    private final ContentConverter converter;

    @Inject
    public HandlerDispatcher(HandlerResolver resolver, ContentConverter converter, Logger log) {
        this.resolver = resolver;
        this.converter = converter;
        this.log = log;
    }

    @Override
    public void handle(HttpExchange exchange) {
        log.info("Requested URI: {} ", exchange.getRequestURI());
        try {
            var handlerWrapper = resolver.resolve(exchange.getRequestURI());
            var method = methodResolver(handlerWrapper, exchange);
            var body = extractRequestBody(exchange);
            log.info("Request body: {} ", body);

            var returned = doInvokeMethod(method, body, handlerWrapper.getHandler());
            var responseCode = getHttpResponseCode(method);
            sendResponse(exchange, responseCode, returned);

        } catch (Exception e) {
            sendError(exchange, e);
        }
    }

    private static int getHttpResponseCode(Method method) {

        if (!method.isAnnotationPresent(EndpointResponseCode.class)) {
            return HttpStatusCode.OK.getCode();
        }

        return method.getAnnotation(EndpointResponseCode.class).value().getCode();
    }

    private Method methodResolver(HandlerWrapper handlerWrapper, HttpExchange exchange) {

        try {
            return handlerWrapper.getHttpMethodHandler(HttpMethod.valueOf(exchange.getRequestMethod()))
                    .orElseThrow(() -> new UnsupportedMethodException(exchange.getRequestMethod(), exchange.getRequestURI()));
        } catch (IllegalArgumentException e) {
            throw new UnsupportedMethodException(exchange.getRequestMethod());
        }
    }

    private static String extractRequestBody(HttpExchange exchange) {
        try {
            return new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RequestBodyExtractException(e);
        }
    }

    private Object doInvokeMethod(Method method, String body, Object handler) throws InvocationTargetException, IllegalAccessException {
        if (method.getParameters().length == 1) {
            var methodInputClass = method.getParameters()[0].getType();
            try {
                var input = converter.fromJson(body, methodInputClass);
                return method.invoke(handler, input);
            } catch (RequestBodyExtractException e) {
                throw new RequestBodyExtractException(e);
            }
        }

        return method.invoke(handler);
    }

    private void sendError(HttpExchange exchange, Exception exception) {
        log.error("Handler dispatcher error ", exception);
        exchange.getResponseHeaders().add("Content-Type", "text/plain");

        ServerException serverException = findServerException(exception);
        int code = HttpStatusCode.INTERNAL_SERVER_ERROR.getCode();
        byte[] msg = "Internal Server Error".getBytes();

        if (serverException != null) {
            code = serverException.getStatusCode().getCode();
            msg = serverException.getMessage().getBytes();
        }

        try (exchange) {
            exchange.sendResponseHeaders(code, msg.length);
            exchange.getResponseBody().write(msg);
        } catch (IOException e) {
            throw new HandlerException("Error on sending error response", HttpStatusCode.INTERNAL_SERVER_ERROR, e);
        }
    }

    private ServerException findServerException(Exception exception) {
        var cause = exception;
        while (cause != null) {
            if (cause instanceof ServerException e) {
                return e;
            }
            cause = (Exception) cause.getCause();
        }
        return null;
    }

    private void sendResponse(HttpExchange exchange, int responseCode, Object returned) throws IOException {
        if (returned == null) {
            sendResponseNoBody(exchange, responseCode);
            return;
        }
        sendResponseWithBody(exchange, responseCode, converter.toJson(returned));
    }

    private static void sendResponseWithBody(HttpExchange exchange, int responseCode, String response) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(responseCode, response.getBytes().length);
        var os = exchange.getResponseBody();
        os.write(response.getBytes());
        exchange.close();
    }

    private static void sendResponseNoBody(HttpExchange exchange, int responseCode) throws IOException {
        exchange.sendResponseHeaders(responseCode, -1);
        exchange.close();
    }
}