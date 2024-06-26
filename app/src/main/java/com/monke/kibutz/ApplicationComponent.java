package com.monke.kibutz;

import android.content.Context;

import com.example.navigation.Navigator;
import com.monke.auth.di.AuthComponentDeps;
import com.monke.di.AppScope;
import com.monke.firebase.RemoteModule;
import com.monke.identity.Identity;
import com.monke.identity.di.IdentityModule;
import com.monke.main.di.HomeComponentDeps;
import com.monke.profile.di.ProfileComponentDeps;
import com.monke.rental.RentalModule;
import com.monke.rental.di.RentalComponentDeps;
import com.monke.user.UserRepository;
import com.monke.user.di.UserModule;

import dagger.BindsInstance;
import dagger.Component;

@Component(modules = {
        UserModule.class,
        RemoteModule.class,
        IdentityModule.class,
        RentalModule.class,
        ApplicationModule.class
})
@AppScope
public interface ApplicationComponent extends
        AuthComponentDeps,
        ProfileComponentDeps,
        RentalComponentDeps,
        HomeComponentDeps {

    UserRepository getUserRepository();

    Navigator getNavigator();

    @Component.Builder
    interface Builder {
        ApplicationComponent build();
        @BindsInstance
        Builder setContext(Context context);
    }
}
