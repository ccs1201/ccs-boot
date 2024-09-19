package br.com.ccsboot.server;

import br.com.ccsboot.server.handler.HandlerResolver;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import jakarta.inject.Inject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class SimpleHttpServer {

    private static final Logger logger = Logger.getLogger(SimpleHttpServer.class.getName());
    private HttpServer server;
    @Inject
    private HandlerResolver resolver;

    public void start(int port, String contextPath) throws IOException {
        init(port, contextPath);
        server.start();
        logger.info("Server started and listening on port " + server.getAddress().getPort());
    }

    private void init(int port, String contextPath) throws IOException {
        // Cria o servidor HTTP
        server = HttpServer.create(new InetSocketAddress(port), 0);

        // Define o handler para lidar com as requisições
        server.createContext("/", new MyHttpHandler());

        // Configura o executor (um thread pool)
        server.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
    }

    // Classe interna para lidar com as requisições HTTP
    private static class MyHttpHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Resposta simples de "Hello, World!"
            String response = "Hello, World!";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
//            os.close();
            exchange.close();
        }
    }
}
