package br.com.ccsboot.controllers;


import br.com.ccsboot.controllers.models.input.InputTest;
import br.com.ccsboot.controllers.models.output.ResponseTest2;
import br.com.ccsboot.server.EndpointController;
import br.com.ccsboot.server.annotations.Endpoint;
import br.com.ccsboot.server.http.enums.HttpMethod;
import br.com.ccsboot.server.http.enums.HttpStatusCode;
import jakarta.inject.Inject;
import org.slf4j.Logger;

import java.time.OffsetDateTime;

@Endpoint("test/2")
public class Test2 implements EndpointController {

    @Inject
    private Logger log;

    @Endpoint.POST
    public ResponseTest2 test(InputTest request) {
        System.out.println("teste controller 2");
        log.info("Request: {}", request);

        return new ResponseTest2(OffsetDateTime.now(),
                request.mensagem(),
                HttpStatusCode.OK,
                HttpMethod.POST,
                this.getClass());
    }
}
