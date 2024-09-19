package br.com.ccsboot.controllers;

import br.com.ccsboot.server.annotations.Endpoint;
import br.com.ccsboot.server.EndpointController;

@Endpoint("test")
public class TestController implements EndpointController {

    @Endpoint.GET
    public void test() {
        System.out.println("teste controller");
    }
}
