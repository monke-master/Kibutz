package com.monke.user;

import dagger.Binds;
import dagger.Module;

@Module
public interface UserModule {

    @Binds
    UserRepository bindUserRepository(UserRepositoryImpl i);

    @Binds
    UserCacheDataSource bindUserCacheDataSource(UserCacheDataSource i);
}
