package br.com.ccsboot.server.http.enums;

public enum HttpStatusCode {
        OK(200), CREATED(201), ACCEPTED(202), NO_CONTENT(204),
        NOT_FOUND(404), BAD_REQUEST(400), UNSUPPORTED_MEDIA_TYPE(415), UNPROCESSABLE_ENTITY(422),
        INTERNAL_SERVER_ERROR(500);

        private final int code;

        HttpStatusCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }