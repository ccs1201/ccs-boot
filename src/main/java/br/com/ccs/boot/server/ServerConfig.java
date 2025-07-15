package br.com.ccs.boot.server;

public record ServerConfig(
        int port,
        String contextPath
) {
    public static ServerConfig withDefaults() {
        return new ServerConfig(8080, "/");
    }
}
