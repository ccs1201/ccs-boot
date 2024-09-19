package br.com.ccsboot.controllers;

import br.com.ccsboot.server.EndpointController;

@br.com.ccsboot.annotations.EndpointController("test")
public class TestController implements EndpointController {

    public void test() {
        System.out.println("teste controller");
    }
}
