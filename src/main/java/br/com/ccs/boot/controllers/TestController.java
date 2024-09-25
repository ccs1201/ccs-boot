package br.com.ccs.boot.controllers;

import br.com.ccs.boot.server.EndpointController;
import br.com.ccs.boot.server.annotations.Endpoint;
import br.com.ccs.boot.server.annotations.EndpointResponseCode;
import br.com.ccs.boot.server.http.enums.HttpStatusCode;
import br.com.ccs.boot.services.TesteService;
import jakarta.inject.Inject;

@Endpoint("test")
public class TestController implements EndpointController {

    private final TesteService testeService;

    @Inject
    public TestController(TesteService testeService) {
        this.testeService = testeService;
    }

    @Endpoint.GET
    public String test() {
        System.out.println("TesteController#GET");
        return testeService.teste();
    }

    @Endpoint.DELETE
    @EndpointResponseCode(HttpStatusCode.NO_CONTENT)
    public String delete() {
        System.out.println("TesteController#DELETE");
        return testeService.delete();
    }
}
