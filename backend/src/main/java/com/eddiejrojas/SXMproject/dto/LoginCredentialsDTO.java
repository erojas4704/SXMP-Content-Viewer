package com.eddiejrojas.sxmproject.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginCredentialsDTO {
    private String username;
    private String password;

    public String toString() {
        return String.format("Username: %s Pass: %s", this.getUsername(), this.getPassword());
    }
}
