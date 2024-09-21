package br.com.ccs.boot.services;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TesteService {

    public String teste() {
        return this.getClass().getName();
    }
}
