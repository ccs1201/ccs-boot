package br.com.ccs.boot.support.json.converter.impl;

import br.com.ccs.boot.support.exceptions.RequestBodyExtractException;
import br.com.ccs.boot.support.json.converter.ContentConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;

@Default
public class JsonContentConverterImpl implements ContentConverter {

    private final ObjectMapper objectMapper;

    @Inject
    public JsonContentConverterImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RequestBodyExtractException(e);
        }
    }

    @Override
    public String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RequestBodyExtractException(e);
        }
    }
}
