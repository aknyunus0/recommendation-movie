package com.film.oneri.onerifilm.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.film.oneri.onerifilm.model.Movie;
import com.film.oneri.onerifilm.model.MovieData;
import com.film.oneri.onerifilm.model.User;
import com.film.oneri.onerifilm.model.UserDto;
import com.film.oneri.onerifilm.repository.MovieRepo;
import com.film.oneri.onerifilm.repository.UserRepo;
import com.film.oneri.onerifilm.service.MovieService;
import com.film.oneri.onerifilm.service.RecommendService;
import com.film.oneri.onerifilm.service.UserService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MovieController {

    private final MovieRepo movieRepo;
    private final MovieService movieService;
    private final UserService userService;
    private final RecommendService recommendService;


    public MovieController(MovieRepo movieRepo, MovieService movieService, UserService userService, RecommendService recommendService, UserRepo userRepo) {
        this.movieRepo = movieRepo;
        this.movieService = movieService;
        this.userService = userService;
        this.recommendService = recommendService;
    }

    @GetMapping("/catalog")
    public String catalog(@AuthenticationPrincipal User user, Model model) {
    	System.out.println("yunus");
        List<Movie> movies = movieRepo.findAll();
        if (user != null) {
            model.addAttribute("user", userService.buildUserDto(user));

        } else {
            model.addAttribute("user", user);
        }
        model.addAttribute("movies", movies);

        return "catalog";
    }

    @PostMapping("/catalog")
    public String addMovie(@RequestParam Map<String, String> form) {
        String imdbId = form.get("imdbId");
      
        
    	HttpRequest request1 = HttpRequest.newBuilder()
    			.uri(URI.create("https://imdb8.p.rapidapi.com/title/v2/get-popular-movies-by-genre?genre="+imdbId+"&limit=25"))
    			.header("X-RapidAPI-Key", "e5cce3cc91msh99454c6fbe09a65p1a52a6jsn46440c2d2ec4")
    			.header("X-RapidAPI-Host", "imdb8.p.rapidapi.com")
    			.method("GET", HttpRequest.BodyPublishers.noBody())
    			.build();
    	HttpResponse<String> response1 = null;
		try {
			response1 = HttpClient.newHttpClient().send(request1, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	String respo=response1.body();
    	
        // JSON dizisini ayrıştırma
    	List<String> result = new ArrayList<>();
        respo = respo.substring(1, respo.length() - 1); // Köşeli parantezleri kaldırma
        String[] tokens = respo.split(",");
        
        for (String token : tokens) {
            String Id = token.replaceAll("[\"/title/tt]", "").replaceAll("/", "");
            System.out.println(Id);
            result.add(Id);

        }
        Collections.reverse(result);
        for (String Id : result) {
            MovieData movieData = movieService.getMovieData(Id);
            movieRepo.save(movieService.convertToMovie(movieData));

        }


        return "redirect:/catalog";
    }

    @GetMapping("/recommended")
    public String recommended(@AuthenticationPrincipal User user, Model model) {
        ArrayList<ArrayList<Movie>> recommended = recommendService.getRecommended(user);
        model.addAttribute("recm", recommended.get(0));
        model.addAttribute("recs", recommended.get(1));
        model.addAttribute("user", userService.buildUserDto(user));
        return "recommendations";
    }

    @GetMapping("/item/{id}")
    public String showMoviePage(
            @PathVariable("id") long id,
            Model model,
            @AuthenticationPrincipal User user) {
        Movie movie = movieService.geMovieFromDb(id);
        if (user != null) {
            String userReview = movie.getTemporaryReview().get(user.getId());
            String approved = "false";
            if (movie.getReview().containsKey(user.getId())) {
                userReview = movie.getReview().get(user.getId());
                approved = "true";
            }
            movie.getReview().remove(user.getId());
            boolean isFavorite = user.getFavouriteList().contains(movie.getId());
            model.addAttribute("user", userService.buildUserDto(user));
            model.addAttribute("userReview", userReview);

            model.addAttribute("approved", approved);
            model.addAttribute("isFavourite", isFavorite);
        }
        model.addAttribute("movie", movie);

        model.addAttribute("reviews", movieService.getUserReviews(movie));
        return "item";
    }

    @PostMapping("/item/{id}")
    public String addTempComment(@PathVariable("id") long id, @AuthenticationPrincipal User user, @RequestParam Map<String, String> form) {
        String comment = form.get("comment");
        User foundUser = userService.getUserFromDb(user);
        Movie movie = movieService.geMovieFromDb(id);
        movie.getTemporaryReview().put(foundUser.getId(), comment);

        movieRepo.save(movie);

        return "redirect:/item/" + id;
    }


    @GetMapping("/delete/item-{itemId}/{userId}")
    public String deleteComment(@PathVariable("userId") long userId, @PathVariable("itemId") long itemId) {
        Movie movie = movieService.geMovieFromDb(itemId);
        movie.getTemporaryReview().remove(userId);
        movie.getReview().remove(userId);

        movieRepo.save(movie);

        return "redirect:/item/" + itemId;
    }

    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable("id") long id) {
        movieRepo.deleteById(id);

        return "redirect:/catalog";
    }

}
