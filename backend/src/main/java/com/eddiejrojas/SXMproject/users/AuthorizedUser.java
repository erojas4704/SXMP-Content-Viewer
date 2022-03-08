package com.eddiejrojas.SXMproject.users;

import java.util.Date;

/**
 * An user with a token and role, to be sent to the client.
 */
public class AuthorizedUser {
    String token;
    String username;
    Date expiration;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }
}
