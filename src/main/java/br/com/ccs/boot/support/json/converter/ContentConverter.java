package br.com.ccs.boot.support.json.converter;

public interface ContentConverter {
    <T> T fromJson(String json, Class<T> clazz);

    String toJson(Object object);
}
