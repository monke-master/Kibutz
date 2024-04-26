package com.monke.auth.di;

import com.monke.auth.ui.AuthFragment;
import com.monke.user.UserModule;

import dagger.Component;

@Component(modules = {
        UserModule.class
})
public interface AuthComponent {

    void inject(AuthFragment authFragment);

    @Component.Builder
    interface Builder {
        AuthComponent build();
    }
}
