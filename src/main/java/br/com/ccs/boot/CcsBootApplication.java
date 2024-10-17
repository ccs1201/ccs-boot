package br.com.ccs.boot;

import br.com.ccs.boot.server.ServerLauncher;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CcsBootApplication {

    private static final Logger logger = LoggerFactory.getLogger(CcsBootApplication.class);

    public static void run(Class<?> mainClass, String[] args) {

        try {
            // Inicializa o container CDI
            var initializer = SeContainerInitializer.newInstance();
            initializer.addPackages(true, mainClass);
            var container = initializer.initialize();
            // Injeta o SimpleHttpServer via CDI
            var serverLauncher = container.select(ServerLauncher.class).get();
            serverLauncher.start(8080, "/");
        } catch (Exception e) {
            logger.error("Server start fail", e);
            System.exit(999);
        }
    }
}
