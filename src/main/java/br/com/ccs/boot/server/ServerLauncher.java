package br.com.ccs.boot.server;

import br.com.ccs.boot.server.http.SimpleHttpServer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;

@ApplicationScoped
public class ServerLauncher {

    private final Logger log;
    private final SimpleHttpServer simpleHttpServer;

    @Inject
    public ServerLauncher(SimpleHttpServer simpleHttpServer, Logger log) {
        this.simpleHttpServer = simpleHttpServer;
        this.log = log;
    }

    public void start(int port, String contextPath) {
        try {
            simpleHttpServer.start(port, contextPath);
        } catch (Exception e) {
            log.error("HTTP server start fail", e);
        }
    }
}
