package br.com.ccs.boot.annotations;

import jakarta.enterprise.context.ApplicationScoped;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark a class as an endpoint handler.
 * The value attribute specifies the base path for the endpoint.
 * <p>
 * The @GET, @POST, @PUT, and @DELETE annotations are used to mark methods as handling HTTP requests for respective HTTP methods.
 * <p>
 * <p>
 * If a class is annotated with &#64; Endpoint("api/v1") then the base path will be api/v1
 * all requests made to this path will be handled by the methods annotated with corespondent http method.
 * </p>
 *
 * @author cleber.souza
 * @version 1.0
 * @implNote </br>
 * <p>Supported HTTP methods are: GET, POST, PUT, DELETE</p>
 * @since 23/09/2024
 */
@ApplicationScoped
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Endpoint {
    String value();

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface GET {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface POST {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface PUT {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface DELETE {
    }

}
