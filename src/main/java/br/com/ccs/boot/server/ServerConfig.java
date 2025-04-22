package br.com.ccs.boot.server;

public record ServerConfig(
        int port,
        String contextPath
) {
    public static ServerConfig defaults() {
        return new ServerConfig(8080, "/");
    }
}
