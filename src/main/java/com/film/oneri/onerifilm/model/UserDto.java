package com.film.oneri.onerifilm.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {
    private long id;
    private String email;
    private String name;
    private String confirmationCode;
    private boolean nonBlocked;
    private boolean nonRestricted;
    private String role;
    private List<Long> favouriteList;
}
