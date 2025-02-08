package com.bchat.model.dto;

import lombok.Data;

@Data
public class LoginDto {

    private String username;
    private String password;
    private String email;

    public String getUsernameOrEmail() {
        return (username != null && !username.isEmpty()) ? username : email;
    }

}
