package com.monke.identity.di;

import com.monke.identity.IdentityCacheDataSource;
import com.monke.identity.IdentityCacheDataSourceImpl;
import com.monke.identity.IdentityRepository;
import com.monke.identity.IdentityRepositoryImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface IdentityModule {

    @Binds
    IdentityRepository bindIdentityRepository(IdentityRepositoryImpl i);

    @Binds
    IdentityCacheDataSource bindIdentityCacheSDataSource(IdentityCacheDataSourceImpl i);
}
