package br.com.ccs.boot.services;

import br.com.ccs.boot.services.exceptions.ServiceException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;

@ApplicationScoped
public class TesteService {

    private final Logger log;

    @Inject
    public TesteService(Logger log) {
        this.log = log;
    }

    public String teste() {
        return this.getClass().getName();
    }

    public String delete() {
        log.info("service DELETE chamado");
        throw new ServiceException("DELETE nao suportado.");
    }
}
