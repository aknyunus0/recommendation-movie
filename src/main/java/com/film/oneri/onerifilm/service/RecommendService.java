package com.film.oneri.onerifilm.service;

import org.springframework.stereotype.Service;

import com.film.oneri.onerifilm.model.Movie;
import com.film.oneri.onerifilm.model.User;
import com.film.oneri.onerifilm.repository.MovieRepo;
import com.film.oneri.onerifilm.repository.UserRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.getCardinalityMap;

@Service
public class RecommendService {
    private final MovieRepo movieRepo;

    public RecommendService(MovieRepo movieRepo) {
        this.movieRepo = movieRepo;
    }

    public ArrayList<ArrayList<Movie>> getRecommended(User user) {
        Map<String, Integer> userCardinalityMap = getUserCardinalityMap(user);
        List<String> favGenres = new ArrayList<>(userCardinalityMap.keySet());

        List<Movie> allRepo = movieRepo.findAll();

        List<Movie> recMovie = allRepo.stream()
                .filter(movie -> movie.getGenres().contains(favGenres.get(0)))
                .filter(movie -> movie.getGenres().contains(favGenres.get(1)))
                .filter(movie -> !user.getFavouriteList().contains(movie.getId()))
                .collect(Collectors.toList());

        List<Movie> recMovieSecondary = allRepo.stream()
                .filter(movie -> movie.getGenres().contains(favGenres.get(0)) || movie.getGenres().contains(favGenres.get(1)))
                .filter(movie -> !recMovie.contains(movie))
                .filter(movie -> !user.getFavouriteList().contains(movie.getId()))
                .collect(Collectors.toList());

        ArrayList<ArrayList<Movie>> lists = new ArrayList<>();
        lists.add((ArrayList<Movie>) recMovie);
        lists.add((ArrayList<Movie>) recMovieSecondary);

        return lists;
    }

    private Map<String, Integer> getUserCardinalityMap(User user) {
        List<Movie> moviesInFavourite = movieRepo.findAll().stream()
                .filter(movie -> user.getFavouriteList().contains(movie.getId()))
                .toList();
        List<String> genresList = new ArrayList<>();
        for (Movie movie : moviesInFavourite) {
            genresList.addAll(movie.getGenres());
        }

        return getCardinalityMap(genresList);
    }
}
