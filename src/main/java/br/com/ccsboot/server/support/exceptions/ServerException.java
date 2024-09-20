package br.com.ccsboot.server.support.exceptions;

import br.com.ccsboot.server.http.enums.HttpStatusCode;

public class ServerException extends RuntimeException {

    private final HttpStatusCode statusCode;

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    public ServerException(String message, HttpStatusCode statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public ServerException(String message, HttpStatusCode statusCode, Throwable e) {
        super(message, e);
        this.statusCode = statusCode;
    }
}
