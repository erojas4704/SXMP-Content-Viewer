package com.eddiejrojas.SXMproject.users;

import com.eddiejrojas.SXMproject.reactions.Reaction;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "username"), @UniqueConstraint(columnNames = "handle")})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(length = 128, nullable = false)
    private String password;
    @Column(nullable = false)
    private String handle;
    private Role role;

    @Column
    private String avatarURL;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL
    )
    private Set<Reaction> reactions;

    public User() {
    }

    public User(String username, String handle, String password) {
        this.username = username;
        this.handle = handle;
        this.password = password;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String value) {
        this.username = value;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String value) {
        this.password = value;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //TODO extend the model so that we can have appropriate permissions for admins
        return null;
    }

    public String getHandle() {
        return this.handle;
    }

    public void setHandle(String value) {
        this.handle = value;
    }

    public String getAvatarURL() {
        return this.avatarURL;
    }

    public void setAvatarURL(String value) {
        this.avatarURL = value;
    }

    public Set<Reaction> getReactions() {
        return reactions;
    }

    public void setReactions(Set<Reaction> reactions) {
        this.reactions = reactions;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String toString() {
        return String.format("[%s: E-mail: %s]", this.getUsername(), this.getHandle());
    }
}
