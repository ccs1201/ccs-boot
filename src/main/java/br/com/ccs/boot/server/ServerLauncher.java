package br.com.ccs.boot.server;

import br.com.ccs.boot.server.http.SimpleHttpServer;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.Objects;

@Singleton
public class ServerLauncher {

    private final SimpleHttpServer simpleHttpServer;

    @Inject
    public ServerLauncher(SimpleHttpServer simpleHttpServer) {
        this.simpleHttpServer = simpleHttpServer;
    }

    public void start(Integer port, String contextPath) throws Exception {
        var config = ServerConfig.defaults();
        if (Objects.nonNull(port) && Objects.nonNull(contextPath)) {
            config = new ServerConfig(port, contextPath);
        }
        simpleHttpServer.start(config);
    }
}
