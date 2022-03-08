package com.eddiejrojas.SXMproject.users.models;

public class LoginDetails {
    private String username;
    private String password;

    public LoginDetails(String email, String password) {
        this.username = email;
        this.password = password;
    }

    public void setEmail(String value) {
        this.username = value;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String value) {
        this.password = value;
    }

    public String toString(){
        return String.format("Username: %s Pass: %s", this.getUsername(), this.getPassword());
    }
}
