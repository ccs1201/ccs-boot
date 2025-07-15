package br.com.ccs.boot;

import br.com.ccs.boot.server.ServerLauncher;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CcsBootApplication {

    private static final Logger logger = LoggerFactory.getLogger(CcsBootApplication.class);

    public static void run(Class<?> mainClass, int port, String contextPath) {

        SeContainer container = null;

        try {
            // Inicializa o container CDI apenas com os packages necessários
            var initializer = SeContainerInitializer.newInstance();
            initializer.addPackages(true, mainClass.getPackage());
            initializer.addPackages(true, CcsBootApplication.class.getPackage());
            initializer.disableDiscovery();
            container = initializer.initialize();
            // Injeta o SimpleHttpServer via CDI
            var serverLauncher = container.select(ServerLauncher.class).get();
            serverLauncher.start(port, contextPath);
            waitForShutdown();
        } catch (Exception e) {
            logger.error("Server start fail", e);
            shutdownContainer(container);
            System.exit(999);
        }
    }

    private static void waitForShutdown() throws InterruptedException {
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
                logger.info("Application shutting down...")
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
