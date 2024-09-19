package br.com.ccsboot.controllers;

import br.com.ccsboot.server.EndpointController;
import br.com.ccsboot.server.annotations.Endpoint;

@Endpoint("test/1/2/3")
public class Test123 implements EndpointController {

    @Endpoint.GET
    public String test() {
        return "Teste GET";
    }

    @Endpoint.POST
    public String test2() {
        return "Teste POST";
    }

    @Endpoint.PUT
    public String test3() {
        return "Teste PUT";
    }

    @Endpoint.DELETE
    public String test4() {
        return "Teste DELETE";
    }

}
