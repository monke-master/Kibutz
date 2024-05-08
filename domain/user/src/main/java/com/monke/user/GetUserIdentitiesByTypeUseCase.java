package com.monke.user;

import com.monke.identity.Identity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class GetUserIdentitiesByTypeUseCase {

    @Inject
    public GetUserIdentitiesByTypeUseCase() {

    }

    public List<Identity> execute(User user, List<Identity.Type> types) {
        ArrayList<Identity> result = new ArrayList<>();
        for (Identity identity: user.getProfile().getIdentities()) {
            if (types.contains(identity.getType())) {
                result.add(identity);
            }
        }
        return result;
    }
}
