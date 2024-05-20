package com.monke.user;


import com.monke.identity.Identity;
import com.monke.rental.Rental;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UserRemote {
    public String id;
    public String name;
    public String email;
    public long dateOfBirth;
    public List<String> responsesIds = new ArrayList<>();
    public ProfileRemote profile;
    public String password;
    public List<String> rentalsIds = new ArrayList<>();

    private List<Identity> identities;

    public UserRemote(String id, String name, String email, long dateOfBirth,
                      List<String> responsesIds, Profile profile, String password,
                      List<String> rentalsIds) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.responsesIds = responsesIds;
        this.profile = new ProfileRemote(profile);
        this.password = password;
        this.rentalsIds = rentalsIds;
    }

    public UserRemote() {

    }

    public UserRemote(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.dateOfBirth = user.getDateOfBirth();
        this.responsesIds = user.getResponses().stream().map(i -> i.getResponseId()).collect(Collectors.toList());
        this.profile = new ProfileRemote(user.getProfile());
        this.password = user.getPassword();
        this.rentalsIds = user.getRentals().stream().map(i -> i.getId()).collect(Collectors.toList());
    }

    public User toDomain(List<Rental> rentals) {
        return new User(
                this.id,
                this.name,
                this.email,
                this.dateOfBirth,
                Collections.emptyList(),
                this.profile.toDomain(identities),
                rentals
        );
    }



    public void setIdentities(List<Identity> identities) {
        this.identities = identities;
    }

    public List<Identity> getIdentities() {
        return identities;
    }
}
