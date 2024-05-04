package com.monke.profile;

import com.monke.identity.Identity;

import java.util.List;

public class EditProfileUiState {

    private String bio = "";
    private List<Identity> identities;

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
}
