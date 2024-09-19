package br.com.ccsboot.controllers;


import br.com.ccsboot.controllers.models.output.ResponseTest2;
import br.com.ccsboot.server.EndpointController;
import br.com.ccsboot.server.annotations.Endpoint;
import br.com.ccsboot.server.http.enums.HttpMethod;
import br.com.ccsboot.server.http.enums.HttpStatusCode;

import java.time.OffsetDateTime;

@Endpoint("test/2")
public class Test2 implements EndpointController {

    @Endpoint.POST
    public ResponseTest2 test() {
        System.out.println("teste controller 2");

        return new ResponseTest2(OffsetDateTime.now(),
                "teste belezinha",
                HttpStatusCode.ACCEPTED,
                HttpMethod.POST,
                this.getClass());
    }
}
