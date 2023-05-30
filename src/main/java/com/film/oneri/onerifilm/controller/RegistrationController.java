package com.film.oneri.onerifilm.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.film.oneri.onerifilm.model.Role;
import com.film.oneri.onerifilm.model.User;
import com.film.oneri.onerifilm.repository.UserRepo;
import com.film.oneri.onerifilm.service.MailService;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    public RegistrationController(PasswordEncoder passwordEncoder, UserRepo userRepo) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
        
    }

    @GetMapping
    public String getView() {
        return "registration";
    }

    @PostMapping
    public String registration(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("password_confirm") String passwordConfirm,
            Model model
    ) {
        if (!password.equals(passwordConfirm)) {
            model.addAttribute("error_message", "Passwords not match!");
            return "registration";
        }

        String trim = name.trim();
        String confirmationCode = generateRandomCode();
        User user = new User(
                trim.isEmpty() ? "undefined" : trim,
                email,
                passwordEncoder.encode(password),
                confirmationCode,
                Role.USER
        );

        Optional<User> userOptional = userRepo.findByEmail(email);

        if (userOptional.isPresent()) {
            model.addAttribute("error_message", "User with this email already exists!");
            return "registration";
        }

        userRepo.save(user);

        return "login";
    }

    private String generateRandomCode() {
        return UUID.randomUUID().toString();
    }
}
