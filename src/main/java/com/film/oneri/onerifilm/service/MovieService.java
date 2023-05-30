package com.film.oneri.onerifilm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.film.oneri.onerifilm.model.Movie;
import com.film.oneri.onerifilm.model.MovieData;
import com.film.oneri.onerifilm.repository.MovieRepo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Value("${api.key: API_KEY}")
    private String apiKey;

    private final MovieRepo movieRepo;
    private final UserService userService;
    private final ObjectMapper mapper = new ObjectMapper();

    public MovieService(MovieRepo movieRepo, UserService userService) {
        this.movieRepo = movieRepo;
        this.userService = userService;
    }

    public MovieData getMovieData(String imdbId) throws NullPointerException {
    	   	
    	
        HttpClient httpClient = HttpClient.newBuilder()
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://imdb8.p.rapidapi.com/title/get-overview-details?tconst=tt" + imdbId))
                .header("x-rapidapi-key", apiKey)
                .header("x-rapidapi-host", "imdb8.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        MovieData movieData = null;

        try {
            JsonNode jsonNode = mapper.readTree(Objects.requireNonNull(response).body());
            movieData = mapper.readValue(jsonNode.toString(), MovieData.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return movieData;
    }

    public Movie convertToMovie(
            MovieData md
    ) {
        return new Movie(
                md.getId(),
                md.getTitle().getTitle(),
                md.getPlotSummary().getText(),
                md.getPlotOutline().getText(),
                md.getRatings().getRating(),
                md.getRatings().getRatingCount(),
                md.getTitle().getYear(),
                md.getTitle().getImage().getUrl(),
                md.getGenres()
        );
    }

    public Movie geMovieFromDb(Long id) throws NoSuchElementException {
        return movieRepo.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public List<Movie> getFavouriteMoviesList(List<Long> listId) {
        return movieRepo.findAll().stream()
                .filter(movie -> listId.contains(movie.getId()))
                .collect(Collectors.toList());
    }

    public HashMap<String, String> getUserReviews(Movie movie) {
        ArrayList<Long> userIds = new ArrayList<>(movie.getReview().keySet());
        return userIds.stream()
                .collect(Collectors.toMap(userId ->
                                userService.getUserById(userId).getName(),
                        userId -> movie.getReview().get(userId),
                        (a, b) -> b, HashMap::new)
                );
    }

}
