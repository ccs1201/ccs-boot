package br.com.ccs.boot;

import br.com.ccs.boot.server.ServerLauncher;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CcsBoot {

    private static final Logger logger = LoggerFactory.getLogger(CcsBoot.class);

    public static void main(String[] args) {

        try {
            // Inicializa o container CDI
            SeContainer container = SeContainerInitializer.newInstance().initialize();
            // Injeta o SimpleHttpServer via CDI
            var serverLauncher = container.select(ServerLauncher.class).get();
            serverLauncher.start(8080, "/");
        } catch (Exception e) {
            logger.error("Server start fail", e);
            System.exit(999);
        }
    }
}
