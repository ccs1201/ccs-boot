import java.util.concurrent.CountDownLatch;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import server.WebServer;

public class CcsBoot {

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(1);

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            WebServer server = container.select(WebServer.class).get();
            server.run(8080, null);

            //Adiciona um hook para desligar o servidor ao receber um sinal de interrupção (Ctrl + C)
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                server.stop();
                latch.countDown();
            }));

            //Mantém o thread principal em execução até que o latch seja liberado
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
}
