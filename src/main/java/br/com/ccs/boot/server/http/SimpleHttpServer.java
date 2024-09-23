package br.com.ccs.boot.server.http;

import br.com.ccs.boot.server.handler.HandlerDispatcher;
import com.sun.net.httpserver.HttpServer;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

@Singleton
public class SimpleHttpServer {

    private final Logger log;
    private HttpServer server;
    private final HandlerDispatcher handlerDispatcher;

    @PreDestroy
    public void destroy() {
        stop();
    }

    @Inject
    public SimpleHttpServer(HandlerDispatcher handlerDispatcher, Logger log) {
        this.handlerDispatcher = handlerDispatcher;
        this.log = log;
    }

    public void start(int port, String contextPath) throws IOException {
        log.info("Starting HTTP server");
        configure(port, contextPath);
        server.start();
        log.info("Server started and listening on port: {}", server.getAddress().getPort());
    }

    private void configure(int port, String contextPath) throws IOException {
        if (contextPath == null || contextPath.isBlank()) {
            contextPath = "/";
        }

        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext(contextPath, handlerDispatcher);
        server.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
    }

    public void stop() {
        log.info("Stopping HTTP server");
        if (server != null) {
            server.stop(0);
            log.info("Server stopped");
        }
    }
}
