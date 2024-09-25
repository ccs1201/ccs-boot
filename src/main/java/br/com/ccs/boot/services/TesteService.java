package br.com.ccs.boot.services;

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
        log.info("Call DELETE");
        throw new UnsupportedOperationException("DELETE not supported yet.");
    }
}
