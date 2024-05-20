package com.monke.user;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserRemote {
    private String id;
    private String name;
    private String email;
    private long dateOfBirth;
    private List<String> responsesIds = new ArrayList<>();
    private Profile profile;
    private String password;
    private List<String> rentalsIds = new ArrayList<>();

    public UserRemote(String id, String name, String email, long dateOfBirth,
                      List<String> responsesIds, Profile profile, String password,
                      List<String> rentalsIds) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.responsesIds = responsesIds;
        this.profile = profile;
        this.password = password;
        this.rentalsIds = rentalsIds;
    }

    public UserRemote(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.dateOfBirth = user.getDateOfBirth();
        this.responsesIds = user.getResponses().stream().map(i -> i.getResponseId()).collect(Collectors.toList());
        this.profile = user.getProfile();
        this.password = user.getPassword();
        this.rentalsIds = user.getRentals().stream().map(i -> i.getId()).collect(Collectors.toList());
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<String> getResponsesIds() {
        return responsesIds;
    }

    public void setResponsesIds(List<String> responsesIds) {
        this.responsesIds = responsesIds;
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

    public List<String> getRentalsIds() {
        return rentalsIds;
    }

    public void setRentalsIds(List<String> rentalsIds) {
        this.rentalsIds = rentalsIds;
    }
}
