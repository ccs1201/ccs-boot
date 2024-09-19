package br.com.ccsboot.server.http;

import br.com.ccsboot.server.handler.HandlerResolver;
import br.com.ccsboot.server.handler.HanlderDispatcher;
import com.sun.net.httpserver.HttpServer;
import jakarta.inject.Inject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Logger;

public class SimpleHttpServer {

    private static final Logger logger = Logger.getLogger(SimpleHttpServer.class.getName());
    private HttpServer server;
    @Inject
    private HandlerResolver resolver;
    @Inject
    private HanlderDispatcher hanlderDispatcher;

    public void start(int port, String contextPath) throws IOException {
        init(port, contextPath);
        server.start();
        logger.info("Server started and listening on port " + server.getAddress().getPort());
    }

    private void init(int port, String contextPath) throws IOException {
        // Cria o servidor HTTP
        server = HttpServer.create(new InetSocketAddress(port), 0);

        // Define o handler para lidar com as requisições
        server.createContext("/", hanlderDispatcher);

        // Configura o executor (um thread pool)
        server.setExecutor(ForkJoinPool.commonPool());
    }
}
