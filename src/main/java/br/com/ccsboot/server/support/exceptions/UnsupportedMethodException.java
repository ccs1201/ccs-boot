package br.com.ccsboot.server.support.exceptions;

import br.com.ccsboot.server.http.enums.HttpStatusCode;

public class UnsupportedMethodException extends HandlerException {
    public UnsupportedMethodException(String msg) {
        super(msg, HttpStatusCode.NOT_FOUND);
    }
}
