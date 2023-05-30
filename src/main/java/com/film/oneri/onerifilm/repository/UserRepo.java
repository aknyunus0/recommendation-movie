package com.film.oneri.onerifilm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.film.oneri.onerifilm.model.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByName(String userName);

    Optional<User> findByEmail(String email);

    Optional<User> findByConfirmationCode(String code);
}
