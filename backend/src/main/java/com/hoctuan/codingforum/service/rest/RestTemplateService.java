package com.hoctuan.codingforum.service.rest;

import java.util.Map;

public interface RestTemplateService {
    public <T> T get(String url, Map<String, String> params, Class<T> responseType);

    public <T> T post(String url, Object requestBody, Map<String, String> params, Class<T> responseType);

    public <T> T put(String url, Object requestBody, Map<String, String> params, Class<T> responseType);

    public void delete(String url, Map<String, String> params);
}