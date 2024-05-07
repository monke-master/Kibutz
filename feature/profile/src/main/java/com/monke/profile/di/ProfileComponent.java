package com.monke.profile.di;

import com.monke.identity.di.IdentityModule;
import com.monke.profile.EditProfileFragment;
import com.monke.profile.IdentitiesFragment;
import com.monke.profile.ProfileFragment;
import com.monke.user.di.UserModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {
        UserModule.class,
        IdentityModule.class

})
@Singleton
public interface ProfileComponent {

    void inject(ProfileFragment fragment);
    void inject(EditProfileFragment fragment);
    void inject(IdentitiesFragment fragment);

    @Component.Builder
    interface Builder {
        ProfileComponent build();


    }
}
