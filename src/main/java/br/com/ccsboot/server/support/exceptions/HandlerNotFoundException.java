package br.com.ccsboot.server.support.exceptions;

import br.com.ccsboot.server.http.enums.HttpStatusCode;

public class HandlerNotFoundException extends HandlerException {

    public HandlerNotFoundException(String msg) {
        super(msg, HttpStatusCode.NOT_FOUND);
    }
}
