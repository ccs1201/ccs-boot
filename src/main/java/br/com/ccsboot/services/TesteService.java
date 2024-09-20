package br.com.ccsboot.services;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TesteService {

    public String teste() {
        return this.getClass().getName();
    }
}
