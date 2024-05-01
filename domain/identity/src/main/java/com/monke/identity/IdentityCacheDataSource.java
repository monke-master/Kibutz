package com.monke.identity;

import java.util.Optional;

public interface IdentityCacheDataSource {

    Optional<Identity> getIdentityById(String id);
}
