package com.film.oneri.onerifilm.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.film.oneri.onerifilm.model.Role;
import com.film.oneri.onerifilm.model.User;
import com.film.oneri.onerifilm.repository.UserRepo;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class AdminController {

    private final UserRepo userRepo;

    public AdminController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public String getAdminPage(
            Model model,
            @AuthenticationPrincipal User user
    ) {
        List<User> users = userRepo.findAll();
        users.removeIf(user1 -> user1.getId() == user.getId());
        model.addAttribute("users", users);

        return "admin-control";
    }

    @GetMapping("/add-admin/{id}")
    public String makeAdmin(
            @PathVariable String id
    ) {
        long userId = Long.parseLong(id);
        User user = userRepo.findById(userId).orElseThrow(NoSuchElementException::new);
        user.setRole(Role.ADMIN);
        userRepo.save(user);

        return "redirect:/admin";
    }

    @GetMapping("/add-moderator/{id}")
    public String makeModer(
            @PathVariable String id
    ) {
        long userId = Long.parseLong(id);
        User user = userRepo.findById(userId).orElseThrow(NoSuchElementException::new);
        user.setRole(Role.MODERATOR);
        userRepo.save(user);

        return "redirect:/admin";
    }

    @GetMapping("/delete-admin/{id}")
    public String deleteAdmin(
            @PathVariable String id
    ) {
        long userId = Long.parseLong(id);
        User user = userRepo.findById(userId).orElseThrow(NoSuchElementException::new);
        user.setRole(Role.USER);
        userRepo.save(user);

        return "redirect:/admin";
    }
}
