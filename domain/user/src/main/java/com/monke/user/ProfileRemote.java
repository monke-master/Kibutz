package com.monke.user;

import com.monke.identity.Identity;

import java.util.List;
import java.util.stream.Collectors;

public class ProfileRemote {

    public String id;
    public List<String> photosUrl;
    public String bio;
    public List<String> identitiesIds;

    public ProfileRemote() {

    }

    public ProfileRemote(Profile profile) {
        this.id = profile.getId();
        this.photosUrl = profile.getPhotosUrl();
        this.bio = profile.getBio();
        this.identitiesIds = profile.getIdentities().stream().map(i -> i.getId()).collect(Collectors.toList());
    }

    public Profile toDomain(List<Identity> identities) {
        return new Profile(
                this.id,
                this.photosUrl,
                this.bio,
                identities
        );
    }
}
