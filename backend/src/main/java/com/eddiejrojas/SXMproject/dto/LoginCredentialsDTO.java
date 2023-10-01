package com.eddiejrojas.sxmproject.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginCredentialsDTO {
    private String username;
    private String password;

    public LoginCredentialsDTO(String email, String password) {
        this.username = email;
        this.password = password;
    }

    public String toString() {
        return String.format("Username: %s Pass: %s", this.getUsername(), this.getPassword());
    }
}
