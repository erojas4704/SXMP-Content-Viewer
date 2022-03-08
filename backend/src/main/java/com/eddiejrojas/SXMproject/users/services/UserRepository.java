package com.eddiejrojas.SXMproject.users.services;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.eddiejrojas.SXMproject.users.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
