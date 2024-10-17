package br.com.ccs.boot.controllers;

import br.com.ccs.boot.server.annotations.Endpoint;
import br.com.ccs.boot.server.annotations.EndpointResponseCode;
import br.com.ccs.boot.server.http.enums.HttpStatusCode;
import jakarta.inject.Inject;
import org.slf4j.Logger;

@Endpoint("test/1/2/3")
public class Test123 {

    private final Logger log;

    @Inject
    public Test123(Logger log) {
        this.log = log;
    }

    @Endpoint.GET
    public String testGet() {
        return "Teste GET";
    }

    @Endpoint.POST
    @EndpointResponseCode(HttpStatusCode.CREATED)
    public String testPost() {
        return "Teste POST";
    }

    @Endpoint.PUT
    @EndpointResponseCode(HttpStatusCode.ACCEPTED)
    public String testPut() {
        return "Teste PUT";
    }

    @Endpoint.DELETE
    @EndpointResponseCode(HttpStatusCode.NO_CONTENT)
    public void testDelete() {
        log.info("Teste DELETE");
    }

}
