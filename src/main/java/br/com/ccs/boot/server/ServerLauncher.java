package br.com.ccs.boot.server;

import br.com.ccs.boot.server.http.SimpleHttpServer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Objects;

@ApplicationScoped
public class ServerLauncher {

    private final SimpleHttpServer simpleHttpServer;

    @Inject
    public ServerLauncher(SimpleHttpServer simpleHttpServer) {
        this.simpleHttpServer = simpleHttpServer;
    }

    public void start(Integer port, String contextPath) throws Exception {
        if (Objects.nonNull(port) && Objects.nonNull(contextPath)) {
            simpleHttpServer.start(new ServerConfig(port, contextPath));
        }
        simpleHttpServer.start(ServerConfig.defaults());
    }
}
