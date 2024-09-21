package br.com.ccs.boot.server.support.exceptions;

import br.com.ccs.boot.server.http.enums.HttpStatusCode;

public class HandlerException extends ServerException {

    public HandlerException(String message, HttpStatusCode statusCode) {
        super(message, statusCode);
    }


    public HandlerException(String msg, HttpStatusCode httpStatusCode, Throwable e) {
        super(msg, httpStatusCode, e);
    }
}
