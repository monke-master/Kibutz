package com.monke.profile;

import com.monke.identity.Identity;

import java.util.ArrayList;
import java.util.List;

public class EditProfileUiState {

    private String bio = "";
    private ArrayList<Identity> identities = new ArrayList<>();

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<Identity> getIdentities() {
        return identities;
    }

    public void addIdentities(List<Identity> identities) {
        this.identities.addAll(identities);
    }
}
