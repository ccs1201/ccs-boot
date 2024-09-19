package br.com.ccsboot.controllers.models.output;

import br.com.ccsboot.server.http.enums.HttpMethod;
import br.com.ccsboot.server.http.enums.HttpStatusCode;

import java.time.OffsetDateTime;

public record ResponseTest2(OffsetDateTime dataHora, String mensagem, HttpStatusCode status, HttpMethod method,
                            Class<?> controllerClass) {
}
