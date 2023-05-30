package com.film.oneri.onerifilm.service;

import org.springframework.stereotype.Service;

import com.film.oneri.onerifilm.model.User;
import com.film.oneri.onerifilm.model.UserDto;
import com.film.oneri.onerifilm.repository.UserRepo;

import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User getUserFromDb(User user) {
        return userRepo.findByEmail(user.getEmail()).orElseThrow(NoSuchElementException::new);
    }

    public User getUserById(Long id) {
        return userRepo.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public UserDto buildUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getConfirmationCode(),
                user.isNonBlocked(),
                user.isNonRestricted(),
                user.getRole().name(),
                user.getFavouriteList());

    }
}
