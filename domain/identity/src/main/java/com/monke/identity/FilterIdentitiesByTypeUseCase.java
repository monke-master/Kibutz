package com.monke.identity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class FilterIdentitiesByTypeUseCase {

    @Inject
    public FilterIdentitiesByTypeUseCase() {

    }

    public List<Identity> execute(List<Identity> identities,
                                  List<Identity.Type> types,
                                  List<String> unavailableIdentities) {
        ArrayList<Identity> result = new ArrayList<>();
        for (Identity identity: identities) {
            if (types.contains(identity.getType()) && !unavailableIdentities.contains(identity.getId())) {
                result.add(identity);
            }
        }
        return result;
    }
}
