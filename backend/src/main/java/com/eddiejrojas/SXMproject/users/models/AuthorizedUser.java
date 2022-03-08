package com.eddiejrojas.SXMproject.users.models;

import java.util.Date;

public class AuthorizedUser {
    private String token;
    private String username;
    private Date expires;

    public AuthorizedUser() {
    }

    public AuthorizedUser(String username, String token, Date expires) {
        this.username = username;
        this.token = token;
        this.expires = expires;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public Date getExpires() {
        return expires;
    }
}
