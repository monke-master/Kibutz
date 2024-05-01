package com.monke.identity;

import java.util.Optional;

public interface IdentityRepository {

    Optional<Identity> getIdentityById(String id);
}
