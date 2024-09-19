package br.com.ccsboot.server.support.exceptions;

import br.com.ccsboot.server.http.enums.HttpStatusCode;

public class HandlerException extends ServerException {

    public HandlerException(String message) {
        super(message);
    }

    public HandlerException(String message, HttpStatusCode statusCode) {
        super(message, statusCode);
    }

    public HandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public HandlerException(Throwable cause) {
        super(cause);
    }
}
