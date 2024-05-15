package com.monke.rental.newrental;

import com.monke.identity.Identity;

import java.util.ArrayList;
import java.util.List;

public class FlatmatesUiState {
    private Integer flatmatesCount = null;
    private List<Identity> identityFilters = new ArrayList<>();

    public Integer getFlatmatesCount() {
        return flatmatesCount;
    }

    public void setFlatmatesCount(Integer flatmatesCount) {
        this.flatmatesCount = flatmatesCount;
    }

    public List<Identity> getIdentityFilters() {
        return identityFilters;
    }

    public void setIdentityFilters(List<Identity> identityFilters) {
        this.identityFilters = identityFilters;
    }
}
