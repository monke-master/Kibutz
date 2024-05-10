package com.monke.rental;

import dagger.Binds;
import dagger.Module;

@Module
public interface RentalModule {

    @Binds
    RentalRepository bindRentalRepository(RentalRepositoryImpl i);

    @Binds
    RentalCacheDataSource bindRentalCacheDataSource(RentalCacheDataSourceImpl i);
}
