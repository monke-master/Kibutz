package com.monke.identity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

public class IdentityCacheDataSourceImpl implements IdentityCacheDataSource {

    private ArrayList<Identity> identities;

    @Inject
    public IdentityCacheDataSourceImpl() {
        identities = new ArrayList<>();
        identities.add(Identities.MALE);
        identities.add(Identities.FEMALE);
        identities.add(Identities.SMOKING);
        identities.add(Identities.NO_SMOKING);
        identities.add(Identities.HAS_ANIMALS);
        identities.add(Identities.NO_ANIMALS);
    }

    @Override
    public Optional<Identity> getIdentityById(String id) {
        return identities.stream().filter(identity -> identity.getId().equals(id)).findFirst();
    }

    @Override
    public List<Identity> getIdentities() {
        return identities;
    }
}
