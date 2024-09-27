package br.com.ccs.boot.services.exceptions;

import br.com.ccs.boot.server.http.enums.HttpStatusCode;
import br.com.ccs.boot.server.support.exceptions.CcsBootCustomException;

public class ServiceException extends CcsBootCustomException {

    public ServiceException(String s, HttpStatusCode httpStatusCode) {
        super(s, httpStatusCode);
    }

    public ServiceException(String s) {
        super(s);
    }
}
