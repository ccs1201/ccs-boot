package br.com.ccs.boot.server;

import br.com.ccs.boot.server.http.SimpleHttpServer;

import java.util.Objects;

import static java.util.Objects.*;

public class ServerLauncher {

    private final SimpleHttpServer simpleHttpServer;

    public ServerLauncher(SimpleHttpServer simpleHttpServer) {
        this.simpleHttpServer = simpleHttpServer;
    }

    public void start(Integer port, String contextPath) throws Exception {
        var config = ServerConfig.withDefaults();
        if (nonNull(port) && nonNull(contextPath)) {
            config = new ServerConfig(port, contextPath);
        }
        simpleHttpServer.start(config);
    }
    
    public void stop() {
        simpleHttpServer.stop();
    }
}
