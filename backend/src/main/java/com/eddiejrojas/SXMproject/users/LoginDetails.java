package com.eddiejrojas.SXMproject.users;

public class LoginDetails {
    private String email;
    private String password;

    LoginDetails(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void setEmail(String value) {
        this.email = value;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String value) {
        this.password = value;
    }

    public String toString(){
        return String.format("Email: %s Pass: %s", this.getEmail(), this.getPassword());
    }
}
