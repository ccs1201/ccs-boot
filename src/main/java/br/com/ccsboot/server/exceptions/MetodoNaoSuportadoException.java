package br.com.ccsboot.server.exceptions;

public class MetodoNaoSuportadoException extends RuntimeException {
    public MetodoNaoSuportadoException(String msg) {
        super(msg);
    }
}
