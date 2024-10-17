package br.com.ccs.boot.support.exceptions;

import br.com.ccs.boot.server.http.enums.HttpStatusCode;

public class RequestBodyExtractException extends HandlerException {

    private static final String MSG ="Error extracting request body";

    public RequestBodyExtractException(Exception e) {
        super(MSG, HttpStatusCode.UNPROCESSABLE_ENTITY, e);
    }
}
