package com.monke.profile.di;

import com.monke.profile.ProfileFragment;
import com.monke.user.di.UserModule;

import dagger.Component;

@Component(modules = {
        UserModule.class
})
public interface ProfileComponent {

    void inject(ProfileFragment fragment);

    @Component.Builder
    interface Builder {
        ProfileComponent build();


    }
}
