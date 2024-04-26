package com.monke.user;

import com.monke.rental.Rental;

import java.util.List;
import java.util.Objects;

public class User {
    private String id;
    private String name;
    private long dateOfBirth;
    private List<Rental> responses;
    private Profile profile;

    public User(String id, String name, long dateOfBirth, List<Rental> responses, Profile profile) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.responses = responses;
        this.profile = profile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return dateOfBirth == user.dateOfBirth &&
               Objects.equals(id, user.id) &&
               Objects.equals(name, user.name) &&
               Objects.equals(responses, user.responses) &&
               Objects.equals(profile, user.profile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dateOfBirth, responses, profile);
    }
}
