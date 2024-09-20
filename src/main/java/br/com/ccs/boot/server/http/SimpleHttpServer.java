package br.com.ccs.boot.server.http;

import br.com.ccs.boot.server.handler.HandlerDispatcher;
import com.sun.net.httpserver.HttpServer;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

@Singleton
public class SimpleHttpServer {

    private final Logger logger;
    private HttpServer server;
    private final HandlerDispatcher handlerDispatcher;

    @Inject
    public SimpleHttpServer(HandlerDispatcher handlerDispatcher, Logger logger) {
        this.handlerDispatcher = handlerDispatcher;
        this.logger = logger;
    }

    public void start(int port, String contextPath) throws IOException {
        configure(port, contextPath);
        server.start();
        logger.info("Server started and listening on port: {}", server.getAddress().getPort());
    }

    private void configure(int port, String contextPath) throws IOException {
        if (contextPath == null || contextPath.isBlank()) {
            contextPath = "/";
        }

        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext(contextPath, handlerDispatcher);
        server.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
    }
}
