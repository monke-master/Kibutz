package com.monke.user;

import com.monke.identity.Identity;
import com.monke.identity.IdentityRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class GetAvailablePositiveIdentitiesUseCase {

    private IdentityRepository identityRepository;

    @Inject
    public GetAvailablePositiveIdentitiesUseCase(
            IdentityRepository identityRepository
    ) {
        this.identityRepository = identityRepository;
    }

    public List<Identity> execute(User user) {
        var identities = identityRepository.getIdentities();
        var result = new ArrayList<Identity>();
        for (Identity identity: identities) {
            if (!user.getProfile().getIdentities().contains(identity) &&
                    identity.getType() == Identity.Type.POSITIVE) {
                result.add(identity);
            }
        }

        return result;
    }
}
