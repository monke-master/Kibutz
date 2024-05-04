package com.monke.profile.di;

import com.monke.profile.EditProfileFragment;
import com.monke.profile.IdentitiesFragment;
import com.monke.profile.ProfileFragment;
import com.monke.user.di.UserModule;

import dagger.Component;

@Component(modules = {
        UserModule.class
})
public interface ProfileComponent {

    void inject(ProfileFragment fragment);
    void inject(EditProfileFragment fragment);
    void inject(IdentitiesFragment fragment);



    @Component.Builder
    interface Builder {
        ProfileComponent build();


    }
}
