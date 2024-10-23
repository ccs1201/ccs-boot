package br.com.ccs.boot.support.exceptions;

import br.com.ccs.boot.server.http.enums.HttpStatusCode;

public class HandlerNotFoundException extends HandlerException {

    public HandlerNotFoundException(String msg) {
        super(msg, HttpStatusCode.NOT_FOUND);
    }
}
