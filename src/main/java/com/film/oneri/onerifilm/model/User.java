package com.film.oneri.onerifilm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "usr")
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private long id;
    @Column(unique = true)
    @Email(message = "Use valid mail")
    private String email;
    private String name;
    private String pass;
    private String confirmationCode;
    private boolean nonBlocked;
    private boolean nonRestricted;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "favourite_list", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "favourite")
    private List<Long> favouriteList;

    public User(String name, String email, String pass, String confirmationCode, Role role) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.role = role;
        this.confirmationCode = confirmationCode;
        this.nonBlocked = true;
        this.nonRestricted = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(role);
    }

    @Override
    public String getPassword() {
        return this.pass;
    }

    @Override
    public String getUsername() {
        return getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.nonBlocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
