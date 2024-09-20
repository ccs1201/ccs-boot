package br.com.ccsboot.test;

import br.com.ccsboot.test.Annotation.HttpParam;
import com.sun.net.httpserver.HttpExchange;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;

import java.util.Map;

public class HttpParams{
    @Produces
    @HttpParam("")
    Map<String,Object> getHttpParams(InjectionPoint injectionPoint) {
        var ex = (HttpExchange) injectionPoint.getMember();
        ex.getHttpContext().getAttributes().forEach((k, v) -> System.out.println(k + " " + v));
        return ex.getHttpContext().getAttributes();
    }
}
