package br.com.ccs.boot.controllers;

import br.com.ccs.boot.server.EndpointController;
import br.com.ccs.boot.server.annotations.Endpoint;
import br.com.ccs.boot.server.annotations.HttpResponseCode;
import br.com.ccs.boot.server.http.enums.HttpStatusCode;
import jakarta.inject.Inject;
import org.slf4j.Logger;

@Endpoint("test/1/2/3")
public class Test123 implements EndpointController {

    private final Logger log;

    @Inject
    public Test123(Logger log) {
        this.log = log;
    }

    @Endpoint.GET
    public String test() {
        return "Teste GET";
    }

    @Endpoint.POST
    @HttpResponseCode(HttpStatusCode.CREATED)
    public String test2() {
        return "Teste POST";
    }

    @Endpoint.PUT
    @HttpResponseCode(HttpStatusCode.ACCEPTED)
    public String test3() {
        return "Teste PUT";
    }

    @Endpoint.DELETE
    @HttpResponseCode(HttpStatusCode.NO_CONTENT)
    public void test4() {
        log.info("Teste DELETE");
    }

}
