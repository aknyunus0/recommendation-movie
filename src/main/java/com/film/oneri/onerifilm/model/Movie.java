package com.film.oneri.onerifilm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Movie {

    @Id
    @GeneratedValue
    private long id;

    private String imdbId;

    private String title;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "text")
    private String description;

    private String shortDescription;

    private String image;

    private double rating;

    private int ratingCount;

    private int year;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "genres")
    private List<String> genres;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "review")
    private Map<Long, String> review;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "temporary_review")
    private Map<Long, String> temporaryReview;

    public Movie(String imdbId, String title, String description, String shortDescription, double rating, int ratingCount, int year, String image, ArrayList<String> genres) {
        this.imdbId = imdbId;
        this.title = title;
        this.description = description;
        this.shortDescription = shortDescription;
        this.rating = rating;
        this.ratingCount = ratingCount;
        this.year = year;
        this.image = image;
        this.genres = genres;
    }
}
