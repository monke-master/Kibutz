package com.monke.user;

import com.monke.rental.Rental;

import java.util.List;
import java.util.Objects;

public class User {
    private String id;
    private String name;
    private String email;
    private long dateOfBirth;
    private List<Rental> responses;
    private Profile profile;

    public User(String email) {
        this.email = email;
    }

    public User(String id, String name, String email, long dateOfBirth,
                List<Rental> responses, Profile profile) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.responses = responses;
        this.profile = profile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<Rental> getResponses() {
        return responses;
    }

    public void setResponses(List<Rental> responses) {
        this.responses = responses;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
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
