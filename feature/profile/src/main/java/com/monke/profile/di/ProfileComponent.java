package com.monke.profile.di;

import com.monke.firebase.RemoteModule;
import com.monke.identity.di.IdentityModule;
import com.monke.profile.EditProfileFragment;
import com.monke.profile.IdentitiesFragment;
import com.monke.profile.ProfileFragment;
import com.monke.profile.UserFragment;
import com.monke.rental.RentalModule;
import com.monke.user.di.UserModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(
        modules = {
                IdentityModule.class,
                RentalModule.class,
                RemoteModule.class
        },
        dependencies = ProfileComponentDeps.class
)
@Singleton
public interface ProfileComponent {

    void inject(ProfileFragment fragment);
    void inject(EditProfileFragment fragment);
    void inject(IdentitiesFragment fragment);
    void inject(UserFragment fragment);

    @Component.Builder
    interface Builder {
        ProfileComponent build();
        Builder setDependencies(ProfileComponentDeps deps);
    }
}
