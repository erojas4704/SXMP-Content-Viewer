package com.eddiejrojas.SXMproject.users;

import jakarta.persistence.*;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private Role role;

    public Long getId(){
        return this.id;
    }

    public void setId(Long value){
        this.id = value;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String value){
        this.username = value;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String value){
        this.email = value;
    }
}
