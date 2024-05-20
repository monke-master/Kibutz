package com.monke.kibutz;

import com.monke.auth.di.AuthComponentDeps;
import com.monke.di.AppScope;
import com.monke.firebase.RemoteModule;
import com.monke.identity.Identity;
import com.monke.identity.di.IdentityModule;
import com.monke.main.di.HomeComponentDeps;
import com.monke.profile.di.ProfileComponentDeps;
import com.monke.rental.di.RentalComponentDeps;
import com.monke.user.UserRepository;
import com.monke.user.di.UserModule;

import dagger.Component;

@Component(modules = {
        UserModule.class,
        RemoteModule.class,
        IdentityModule.class
})
@AppScope
public interface ApplicationComponent extends
        AuthComponentDeps,
        ProfileComponentDeps,
        RentalComponentDeps,
        HomeComponentDeps {

    UserRepository getUserRepository();

    @Component.Builder
    interface Builder {
        ApplicationComponent build();
    }
}
