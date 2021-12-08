package com.example.keycloak_auth_service;

import lombok.Data;

@Data
public class UserDto {

    private String username;
    private String password;
}
