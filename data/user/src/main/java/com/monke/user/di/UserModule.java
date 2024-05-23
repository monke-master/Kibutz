package com.monke.user.di;

import android.content.Context;

import com.monke.user.AuthDataSourceImpl;
import com.monke.user.UserCacheDataSource;
import com.monke.user.UserCacheDataSourceImpl;
import com.monke.user.AuthDataSource;
import com.monke.user.UserPrefDataSource;
import com.monke.user.UserPrefDataSourceImpl;
import com.monke.user.UserRemoteDataSource;
import com.monke.user.UserRemoteDataSourceImpl;
import com.monke.user.UserRepository;
import com.monke.user.UserRepositoryImpl;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public interface UserModule {

    @Binds
    UserRepository bindUserRepository(UserRepositoryImpl i);

    @Binds
    UserCacheDataSource bindUserCacheDataSource(UserCacheDataSourceImpl i);

    @Binds
    AuthDataSource bindUserAuthDataSource(AuthDataSourceImpl i);

    @Binds
    UserRemoteDataSource bindUserRemoteDataSource(UserRemoteDataSourceImpl i);

    @Provides
    static UserPrefDataSource provideUserPrefDataSource(Context context) {
        return new UserPrefDataSourceImpl(context);
    }
}
