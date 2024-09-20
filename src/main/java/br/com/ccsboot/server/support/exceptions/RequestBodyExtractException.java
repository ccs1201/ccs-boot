package br.com.ccsboot.server.support.exceptions;

import br.com.ccsboot.server.http.enums.HttpStatusCode;

public class RequestBodyExtractException extends HandlerException {

    private static final String MSG ="Error extracting request body";

    public RequestBodyExtractException(Exception e) {
        super(MSG, HttpStatusCode.UNPROCESSABLE_ENTITY, e);
    }
}
