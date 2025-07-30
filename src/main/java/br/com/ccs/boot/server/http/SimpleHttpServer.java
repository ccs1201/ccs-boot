package br.com.ccs.boot.server.http;

import br.com.ccs.boot.server.ServerConfig;
import br.com.ccs.boot.server.handler.HandlerDispatcher;
import br.com.ccs.boot.support.exceptions.ServerConfigurationException;
import com.sun.net.httpserver.HttpServer;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.isNull;

public class SimpleHttpServer {

    private final Logger log = LoggerFactory.getLogger(SimpleHttpServer.class);
    private HttpServer server;
    private final HandlerDispatcher handlerDispatcher;
    private final ThreadPoolExecutor executor;

    @PreDestroy
    public void destroy() {
        stop();
    }

    @Inject
    public SimpleHttpServer(HandlerDispatcher handlerDispatcher) {
        this.handlerDispatcher = handlerDispatcher;
        executor = new ThreadPoolExecutor(
                10,
                200,
                60L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(200),
                Thread.ofVirtual().factory());
    }

    public void start(ServerConfig config) throws Exception {
        log.info("Starting HTTP server");
        configure(config);
        server.start();
        log.info("Server started and listening on port: {}", server.getAddress().getPort());
    }

    private void configure(ServerConfig config) throws Exception {
        if (isNull(config.contextPath()) || config.contextPath().isBlank()) {
            throw new ServerConfigurationException("ContextPath must not be null.");
        }

        server = HttpServer.create(new InetSocketAddress(config.port()), 0);
        server.createContext(config.contextPath(), handlerDispatcher);
        server.setExecutor(executor);
    }

    public void stop() {
        if (server == null) return;

        log.info("Stopping HTTP server");

        // Para o servidor primeiro
        server.stop(0);

        // Depois para o executor
        if (executor != null && !executor.isShutdown()) {
            try {
                executor.shutdown();
                if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                log.error("Error while shutting down executor", e);
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }

        log.info("Server stopped");
    }
}
