package br.com.ccsboot.server.http;

import br.com.ccsboot.server.handler.HandlerDispatcher;
import com.sun.net.httpserver.HttpServer;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

@Singleton
public class SimpleHttpServer {

    private static final Logger logger = LoggerFactory.getLogger(SimpleHttpServer.class);
    private HttpServer server;
    private final HandlerDispatcher handlerDispatcher;

    @Inject
    public SimpleHttpServer(HandlerDispatcher handlerDispatcher) {
        this.handlerDispatcher = handlerDispatcher;
    }

    public void start(int port, String contextPath) throws IOException {
        configure(port, contextPath);
        server.start();
        logger.info("Server started and listening on port: {}", server.getAddress().getPort());
    }

    private void configure(int port, String contextPath) throws IOException {
        // Cria o servidor HTTP
        server = HttpServer.create(new InetSocketAddress(port), 0);

        // Define o handler para lidar com as requisições
        if (contextPath == null || contextPath.isBlank()) {
            contextPath = "/";
        }
        server.createContext(contextPath, handlerDispatcher);

        // Configura o executor (um thread pool)
        server.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
    }
}
