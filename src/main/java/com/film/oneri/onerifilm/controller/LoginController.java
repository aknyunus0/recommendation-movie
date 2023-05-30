package com.film.oneri.onerifilm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(
            @RequestParam(name = "error", required = false) String error,
            Model model
    ) {
        if (error == null) {
            model.addAttribute("error_message", "");
        } else {
            model.addAttribute("error_message", "Email or password is wrong! Check it and try again.");
            return "login";
        }

        return "login";
    }
}
