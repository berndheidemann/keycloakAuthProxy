package com.example.keycloak_auth_service.dto;

import lombok.Data;

@Data
public class AuthDtoRequest {

    private String client_id;
    private String grant_type;
    private String username;
    private String password;
}
