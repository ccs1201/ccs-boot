package br.com.ccs.boot.controllers;

import br.com.ccs.boot.server.annotations.Endpoint;
import br.com.ccs.boot.server.EndpointController;
import br.com.ccs.boot.services.TesteService;
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
