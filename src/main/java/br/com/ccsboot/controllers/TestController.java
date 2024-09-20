package br.com.ccsboot.controllers;

import br.com.ccsboot.server.annotations.Endpoint;
import br.com.ccsboot.server.EndpointController;
import br.com.ccsboot.services.TesteService;
import jakarta.inject.Inject;

@Endpoint("test")
public class TestController implements EndpointController {

    @Inject
    private TesteService  testeService;

    @Endpoint.GET
    public String test() {
        System.out.println("teste controller");
        return testeService.teste();
    }
}
