package br.com.ccs.boot.support.exceptions;

import br.com.ccs.boot.server.http.enums.HttpStatusCode;

import java.net.URI;
import java.text.MessageFormat;

public class UnsupportedMethodException extends HandlerException {

    public UnsupportedMethodException(String requestMethod, URI uri) {
        super(MessageFormat.format("No handler found for HTTP method: {0} in path {1}", requestMethod, uri),
                HttpStatusCode.NOT_FOUND);
    }

    public UnsupportedMethodException(String httpMethod) {
        super(MessageFormat.format("HTTP method {0} not supported ", httpMethod), HttpStatusCode.NOT_FOUND);
    }
}
