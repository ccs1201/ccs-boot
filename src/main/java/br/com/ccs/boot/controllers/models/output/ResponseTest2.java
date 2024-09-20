package br.com.ccs.boot.controllers.models.output;

import br.com.ccs.boot.server.http.enums.HttpMethod;
import br.com.ccs.boot.server.http.enums.HttpStatusCode;

import java.time.OffsetDateTime;

public record ResponseTest2(OffsetDateTime dataHora, String mensagem, HttpStatusCode status, HttpMethod method,
                            Class<?> controllerClass) {
}
