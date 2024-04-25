package com.monke.user;

import com.monke.identity.Identity;

import java.util.List;
import java.util.Objects;

public class Profile {
    private String id;
    private List<String> photosUrl;
    private String bio;
    private List<Identity> identities;

    public Profile(String id, List<String> photosUrl, String bio, List<Identity> identities) {
        this.id = id;
        this.photosUrl = photosUrl;
        this.bio = bio;
        this.identities = identities;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getPhotosUrl() {
        return photosUrl;
    }

    public void setPhotosUrl(List<String> photosUrl) {
        this.photosUrl = photosUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<Identity> getIdentities() {
        return identities;
    }

    public void setIdentities(List<Identity> identities) {
        this.identities = identities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return Objects.equals(id, profile.id) &&
               Objects.equals(photosUrl, profile.photosUrl) &&
               Objects.equals(bio, profile.bio) &&
               Objects.equals(identities, profile.identities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, photosUrl, bio, identities);
    }
}
