package com.monke.user;


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

    public User toDomain() {
        return new User(
                this.id,
                this.name,
                this.email,
                this.dateOfBirth,
                Collections.emptyList(),
                Mocks.mockProfile2,
                Collections.emptyList()
        );
    }

}
