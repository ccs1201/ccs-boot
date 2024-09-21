package br.com.ccs.boot.controllers;


import br.com.ccs.boot.controllers.models.input.InputTest;
import br.com.ccs.boot.controllers.models.output.ResponseTest2;
import br.com.ccs.boot.server.EndpointController;
import br.com.ccs.boot.server.annotations.Endpoint;
import br.com.ccs.boot.server.http.enums.HttpMethod;
import br.com.ccs.boot.server.http.enums.HttpStatusCode;
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
