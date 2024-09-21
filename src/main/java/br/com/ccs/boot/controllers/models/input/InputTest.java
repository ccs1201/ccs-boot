package br.com.ccs.boot.controllers.models.input;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record InputTest(OffsetDateTime dataHora, String mensagem, BigDecimal valor, Boolean aBoolean) {
}
