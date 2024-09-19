package br.com.ccsboot;

import br.com.ccsboot.server.ServerLauncher;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class CcsBoot {

    public static void main(String[] args) {

        // Inicializa o container CDI
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            // Injeta o SimpleHttpServer via CDI
            ServerLauncher serverLauncher = container.select(ServerLauncher.class).get();
            serverLauncher.start(8080, "/");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(999);
        }
    }
}
