package com.monke.identity;

import java.util.ArrayList;
import java.util.Optional;

import javax.inject.Inject;

public class IdentityCacheDataSourceImpl implements IdentityCacheDataSource {

    private ArrayList<Identity> identities;

    @Inject
    public IdentityCacheDataSourceImpl() {
        identities.add(Identities.MALE);
        identities.add(Identities.FEMALE);
    }

    @Override
    public Optional<Identity> getIdentityById(String id) {
        return identities.stream().filter(identity -> identity.getId().equals(id)).findFirst();
    }
}
