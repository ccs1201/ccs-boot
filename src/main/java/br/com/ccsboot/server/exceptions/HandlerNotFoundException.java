package br.com.ccsboot.server.exceptions;

public class HandlerNotFoundException extends RuntimeException {
    public HandlerNotFoundException(String msg) {
        super(msg);
    }
}
