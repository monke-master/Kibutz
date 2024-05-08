package com.monke.kibutz;

import com.monke.auth.di.AuthComponentDeps;
import com.monke.di.AppScope;
import com.monke.profile.di.ProfileComponentDeps;
import com.monke.user.UserRepository;
import com.monke.user.di.UserModule;

import dagger.Component;

@Component(modules = {UserModule.class})
@AppScope
public interface ApplicationComponent extends AuthComponentDeps, ProfileComponentDeps {

    UserRepository getUserRepository();

    @Component.Builder
    interface Builder {
        ApplicationComponent build();
    }
}
