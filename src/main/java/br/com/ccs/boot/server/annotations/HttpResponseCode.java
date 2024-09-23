package br.com.ccs.boot.server.annotations;

import br.com.ccs.boot.server.http.enums.HttpStatusCode;

import java.lang.annotation.*;

/**
 * This annotation is used to specify the HTTP response code for a method.
 * It can be used on any method in an EndpointController class.
 *
 * @author cleber.souza
 * @version 1.0
 * @since 23/09/2024
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttpResponseCode {
    HttpStatusCode value() default HttpStatusCode.OK;
}
