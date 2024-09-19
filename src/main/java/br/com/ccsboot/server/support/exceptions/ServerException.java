package br.com.ccsboot.server.support.exceptions;

import br.com.ccsboot.server.http.enums.HttpStatusCode;

public class ServerException extends RuntimeException {

    private HttpStatusCode statusCode = HttpStatusCode.INTERNAL_SERVER_ERROR;

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    public ServerException(String message) {
        super(message);
    }

    public ServerException(String message, HttpStatusCode statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerException(Throwable cause) {
        super(cause);
    }
}
