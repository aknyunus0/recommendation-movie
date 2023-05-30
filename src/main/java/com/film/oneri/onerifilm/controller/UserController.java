package com.film.oneri.onerifilm.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.film.oneri.onerifilm.model.Movie;
import com.film.oneri.onerifilm.model.User;
import com.film.oneri.onerifilm.repository.UserRepo;
import com.film.oneri.onerifilm.service.MovieService;
import com.film.oneri.onerifilm.service.UserService;

import java.util.List;

@Controller
public class UserController {

    private final UserRepo userRepo;
    private final UserService userService;
    private final MovieService movieService;


    public UserController(UserRepo userRepo, UserService userService, com.film.oneri.onerifilm.service.MovieService movieService) {
        this.userRepo = userRepo;
        this.userService = userService;
        this.movieService = movieService;
    }

    @GetMapping("/user/{id}")
    public String getInfo(
            @PathVariable("id") long userId,
            Model model
    ) {
        User user = userService.getUserById(userId);
        List<Long> favouriteListId = user.getFavouriteList();
        List<Movie> movies = movieService.getFavouriteMoviesList(favouriteListId);

        model.addAttribute("user", userService.buildUserDto(user));
        model.addAttribute("movies", movies);
        return "user";
    }

    @GetMapping("/my-account")
    public String viewHome(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        User foundUser = userService.getUserFromDb(user);
        List<Long> favouriteListId = foundUser.getFavouriteList();
        List<Movie> movies = movieService.getFavouriteMoviesList(favouriteListId);

        model.addAttribute("user", userService.buildUserDto(user));
        model.addAttribute("movies", movies);
        return "user";
    }

    @GetMapping("/item/add-favourite/{id}")
    public String addToFavouriteList(
            @PathVariable("id") long id,
            @AuthenticationPrincipal User user
    ) {
        User foundUser = userService.getUserFromDb(user);
        Movie movie = movieService.geMovieFromDb(id);
        if (!foundUser.getFavouriteList().contains(movie.getId())) {
            foundUser.getFavouriteList().add(movie.getId());
        }
        userRepo.save(foundUser);
        return "redirect:/item/" + id;
    }

    @GetMapping("/item/delete-favourite/{id}")
    public String deleteFromFavouriteList(
            @PathVariable("id") long id,
            @AuthenticationPrincipal User user
    ) {
        User foundUser = userService.getUserFromDb(user);
        Movie movie = movieService.geMovieFromDb(id);
        foundUser.getFavouriteList().remove(movie.getId());
        userRepo.save(foundUser);

        return "redirect:/my-account";
    }

    @GetMapping("/logout")
    public String deleteFromFavouriteList() {
        return "login";
    }


}
