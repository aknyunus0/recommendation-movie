package com.film.oneri.onerifilm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.film.oneri.onerifilm.model.Movie;

public interface MovieRepo extends JpaRepository<Movie, Long> {
}
