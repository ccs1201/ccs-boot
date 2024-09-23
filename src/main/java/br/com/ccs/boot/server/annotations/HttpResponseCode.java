package br.com.ccs.boot.server.annotations;

import br.com.ccs.boot.server.http.enums.HttpStatusCode;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttpResponseCode {
    HttpStatusCode value() default HttpStatusCode.OK;
}
