package br.com.ccs.boot.server;

import br.com.ccs.boot.server.http.SimpleHttpServer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class ServerLauncher {

    private static final Logger logger = LoggerFactory.getLogger(ServerLauncher.class);
    private final SimpleHttpServer simpleHttpServer;

    @Inject
    public ServerLauncher(SimpleHttpServer simpleHttpServer) {
        this.simpleHttpServer = simpleHttpServer;
    }

    public void start(int port, String contextPath) {
        // Inicializa o servidor com o CDI gerenciando as dependências
        try {
            simpleHttpServer.start(port, contextPath);
        } catch (Exception e) {
            logger.error("HTTP server start fail", e);
        }
    }
}
