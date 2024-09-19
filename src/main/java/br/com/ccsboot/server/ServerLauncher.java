package br.com.ccsboot.server;

import br.com.ccsboot.server.http.SimpleHttpServer;
import jakarta.inject.Inject;

public class ServerLauncher {

    @Inject
    private SimpleHttpServer simpleHttpServer;

    public void start(int port, String contextPath) throws Exception {
        // Inicializa o servidor com o CDI gerenciando as dependências
        simpleHttpServer.start(port, contextPath);
    }
}
