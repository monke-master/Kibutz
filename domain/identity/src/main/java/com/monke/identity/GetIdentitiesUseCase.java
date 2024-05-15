package com.monke.identity;

import java.util.List;

import javax.inject.Inject;

public class GetIdentitiesUseCase {

    private final IdentityRepository identityRepository;

    @Inject
    public GetIdentitiesUseCase(IdentityRepository identityRepository) {
        this.identityRepository = identityRepository;
    }

    public List<Identity> execute() {
        return identityRepository.getIdentities();
    }
}
