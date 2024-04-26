package com.monke.auth.di;

import com.monke.auth.ui.StartFragment;
import com.monke.user.UserModule;

import dagger.Component;

@Component(modules = { UserModule.class }
)
public interface AuthComponent {

    void inject(StartFragment startFragment);

    @Component.Builder
    interface Builder {
        AuthComponent build();


    }
}
