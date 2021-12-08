package com.example.keycloak_auth_service;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.time.Duration;

import com.example.keycloak_auth_service.dto.AuthDtoRequest;
import com.example.keycloak_auth_service.dto.AuthDtoResponse;
import com.example.keycloak_auth_service.dto.AuthDtoClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthClient {
    String keycloakUrl = "http://keycloak.szut.dev/auth/realms/szut/protocol/openid-connect/token";


    RestTemplate restTemplate;

    public AuthClient(RestTemplateBuilder restTemplateBuilder) {

        this.restTemplate = new RestTemplate();
        final HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        final CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setRedirectStrategy(new LaxRedirectStrategy())
                .build();
        factory.setHttpClient(httpClient);
        restTemplate.setRequestFactory(factory);

//        restTemplate = restTemplateBuilder
//
//                .setConnectTimeout(Duration.ofSeconds(10))
//                .setReadTimeout(Duration.ofSeconds(10))
//                .build();
    }

    public AuthDtoResponse getAuthData(AuthDtoRequest dtoRequest) {
        try {

            HttpHeaders headers = new HttpHeaders();
            // headers.set("Content-Type", "application/x-www-form-urlencoded");
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            // headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            // HttpEntity<String> request = new HttpEntity<String>(headers);


            MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
            map.add("grant_type", dtoRequest.getGrant_type());
            map.add("client_id", dtoRequest.getClient_id());
            map.add("username", dtoRequest.getUsername());
            map.add("password", dtoRequest.getPassword());


            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

            ResponseEntity<AuthDtoResponse> response = restTemplate.postForEntity(keycloakUrl, request, AuthDtoResponse.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw e;
        }
    }
}


