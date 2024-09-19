package br.com.ccsboot;

import br.com.ccsboot.server.SimpleHttpServer;
import jakarta.inject.Inject;

public class ServerLauncher {

    @Inject
    private SimpleHttpServer simpleHttpServer;

    public void start(int port, String contextPath) throws Exception {
        // Inicializa o servidor com o CDI gerenciando as dependências
        simpleHttpServer.start(port, contextPath);
    }
}
