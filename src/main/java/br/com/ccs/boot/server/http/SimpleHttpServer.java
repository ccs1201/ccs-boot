package br.com.ccs.boot.server.http;

import br.com.ccs.boot.server.ServerConfig;
import br.com.ccs.boot.server.handler.HandlerDispatcher;
import br.com.ccs.boot.support.exceptions.ServerConfigurationException;
import com.sun.net.httpserver.HttpServer;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;

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

    public void start(ServerConfig config) throws Exception {
        log.info("Starting HTTP server");
        configure(config);
        server.start();
        log.info("Server started and listening on port: {}", server.getAddress().getPort());
    }

    private void configure(ServerConfig config) throws Exception {
        if (config.contextPath() == null || config.contextPath().isBlank()) {
            throw new ServerConfigurationException("ContextPath must not be null.");
        }

        server = HttpServer.create(new InetSocketAddress(config.port()), 0);
        server.createContext(config.contextPath(), handlerDispatcher);
        server.setExecutor(Executors.newFixedThreadPool(200, Thread.ofVirtual().factory()));
    }

    public void stop() {
        log.info("Stopping HTTP server");
        if (server != null) {
            server.stop(0);
            log.info("Server stopped");
        }
    }
}
