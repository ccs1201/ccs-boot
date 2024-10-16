package br.com.ccs.boot.server;

import br.com.ccs.boot.server.http.SimpleHttpServer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.IOException;

@ApplicationScoped
public class ServerLauncher {

    private final SimpleHttpServer simpleHttpServer;

    @Inject
    public ServerLauncher(SimpleHttpServer simpleHttpServer) {
        this.simpleHttpServer = simpleHttpServer;
    }

    public void start(int port, String contextPath) throws IOException {
        simpleHttpServer.start(port, contextPath);
    }
}
