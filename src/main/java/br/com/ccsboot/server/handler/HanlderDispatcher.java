package br.com.ccsboot.server.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.io.IOException;

@Singleton
public class MyHttpHandler implements HttpHandler {

    @Inject
    private HandlerResolver resolver;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("URI requisitada: " + exchange.getRequestURI());

        var httpMethod = exchange.getRequestMethod();

        var handlerObject = resolver.resolve(exchange.getRequestURI());


        String response = "Hello, World!";
        exchange.sendResponseHeaders(200, response.length());
        var os = exchange.getResponseBody();
        os.write(response.getBytes());
//            os.close();
        exchange.close();
    }
}