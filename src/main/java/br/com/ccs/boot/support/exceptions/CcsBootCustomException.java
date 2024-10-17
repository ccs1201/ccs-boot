package br.com.ccs.boot.support.exceptions;

import br.com.ccs.boot.server.http.enums.HttpStatusCode;

public abstract class CcsBootCustomException extends ServerException {

    protected CcsBootCustomException(String message, HttpStatusCode statusCode) {
        super(message, statusCode);
    }

    protected CcsBootCustomException(String message) {
        super(message, HttpStatusCode.BAD_REQUEST);
    }

    protected CcsBootCustomException(String message, HttpStatusCode statusCode, Throwable e) {
        super(message, statusCode, e);
    }
}
