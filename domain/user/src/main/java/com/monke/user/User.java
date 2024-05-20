package com.monke.user;

import com.monke.rental.Rental;
import com.monke.rental.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    private String id;
    private String name;
    private String email;
    private long dateOfBirth;
    private List<Response> responses = new ArrayList<Response>();
    private Profile profile;
    private String password;
    private List<Rental> rentals = new ArrayList<>();

    public User(String email) {
        this.email = email;
    }

    public User(String id, String name, String email, long dateOfBirth,
                List<Response> responses, Profile profile, List<Rental> rentals) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.responses = responses;
        this.profile = profile;
        this.rentals = rentals;
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

    public List<Response> getResponses() {
        return responses;
    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
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

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", responses=" + responses +
                ", profile=" + profile +
                ", password='" + password + '\'' +
                ", rentals=" + rentals +
                '}';
    }

    public User clone() {
        return new User(
                id,
                name,
                email,
                dateOfBirth,
                new ArrayList<>(responses),
                profile.clone(),
                new ArrayList<>(rentals)
        );
    }
}
