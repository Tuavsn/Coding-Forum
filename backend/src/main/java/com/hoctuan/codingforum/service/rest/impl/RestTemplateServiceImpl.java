package com.hoctuan.codingforum.service.rest.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.hoctuan.codingforum.service.rest.RestTemplateService;

@Service
public class RestTemplateServiceImpl implements RestTemplateService {
    @Autowired
    private RestTemplate restTemplate;

    @Override
     public <T> T get(String url, Map<String, String> params, Class<T> responseType) {
        try {
            HttpEntity<String> entity = new HttpEntity<>(null);
            
            if (params != null && !params.isEmpty()) {
                url = buildUrlWithParams(url, params);
            }

            ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, entity, responseType);
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            System.err.println("HTTP Error: " + ex.getStatusCode());
            System.err.println(ex.getResponseBodyAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T post(String url, Object requestBody, Map<String, String> params, Class<T> responseType) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);

            if (params != null && !params.isEmpty()) {
                url = buildUrlWithParams(url, params);
            }

            ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.POST, entity, responseType);
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            System.err.println("HTTP Error: " + ex.getStatusCode());
            System.err.println(ex.getResponseBodyAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T put(String url, Object requestBody, Map<String, String> params, Class<T> responseType) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);

            if (params != null && !params.isEmpty()) {
                url = buildUrlWithParams(url, params);
            }

            ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.PUT, entity, responseType);
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            System.err.println("HTTP Error: " + ex.getStatusCode());
            System.err.println(ex.getResponseBodyAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(String url, Map<String, String> params) {
        try {
            if (params != null && !params.isEmpty()) {
                url = buildUrlWithParams(url, params);
            }

            restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            System.err.println("HTTP Error: " + ex.getStatusCode());
            System.err.println(ex.getResponseBodyAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String buildUrlWithParams(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder(url);
        if (params != null && !params.isEmpty()) {
            sb.append("?");
            params.forEach((key, value) -> sb.append(key).append("=").append(value).append("&"));
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
    
}
