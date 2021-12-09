package com.example.keycloak_auth_service;

import com.example.keycloak_auth_service.dto.AuthDtoRequest;
import com.example.keycloak_auth_service.dto.AuthDtoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@CrossOrigin
@RequestMapping(value = "/")
public class AuthController {

    private AuthClient authClient;

    public AuthController(AuthClient authClient) {
        this.authClient = authClient;
    }

    @PostMapping(path = "/", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<Object> auth(
            AuthDtoRequest dtoRequest) {

        try {
            AuthDtoResponse response = this.authClient.getAuthData(dtoRequest);
            if (response != null) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }


        return null;
    }

    @GetMapping
    public String hello() {
        return "hello3";
    }

}
