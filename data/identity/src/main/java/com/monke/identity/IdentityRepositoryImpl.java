package com.monke.identity;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

public class IdentityRepositoryImpl implements IdentityRepository {

    private final IdentityCacheDataSource identityCacheDataSource;

    @Inject
    public IdentityRepositoryImpl(IdentityCacheDataSource identityCacheDataSource) {
        this.identityCacheDataSource = identityCacheDataSource;
    }

    @Override
    public Optional<Identity> getIdentityById(String id) {
        return identityCacheDataSource.getIdentityById(id);
    }

    @Override
    public List<Identity> getIdentities() {
        return null;
    }


}
