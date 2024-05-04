package com.monke.identity;

import java.util.List;
import java.util.Optional;

public interface IdentityRepository {

    Optional<Identity> getIdentityById(String id);

    List<Identity> getIdentities();
}
