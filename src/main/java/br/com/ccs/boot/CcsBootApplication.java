package br.com.ccs.boot;

import br.com.ccs.boot.server.ServerLauncher;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CcsBootApplication {

    private static final Logger logger = LoggerFactory.getLogger(CcsBootApplication.class);

    public static void run(Class<?> mainClass, String[] args) {

        SeContainer container = null;

        try {
            // Inicializa o container CDI
            var initializer = SeContainerInitializer.newInstance();
            initializer.addPackages(true, mainClass.getPackage());
            container = initializer.initialize();
            // Injeta o SimpleHttpServer via CDI
            var serverLauncher = container.select(ServerLauncher.class).get();
            serverLauncher.start(8080, "/");
            waitForShutdown();
        } catch (Exception e) {
            logger.error("Server start fail", e);
            shutdownContainer(container);
            System.exit(999);
        }
    }

    private static void waitForShutdown() throws InterruptedException {
        // Registra um shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
                logger.info("Server shutting down...")
        ));

        Thread.currentThread().join();
    }

    private static void shutdownContainer(SeContainer container) {
        if (container != null && container.isRunning()) {
            try {
                container.close();
            } catch (Exception e) {
                logger.error("Error shutting down container", e);
            }
        }
    }
}
