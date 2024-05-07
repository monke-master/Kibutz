package com.monke.auth.di;

import com.monke.auth.ui.email.EmailFragment;
import com.monke.auth.ui.StartFragment;
import com.monke.auth.ui.info.UserInfoFragment;
import com.monke.auth.ui.password.PasswordFragment;
import com.monke.di.AuthScope;
import com.monke.identity.di.IdentityModule;
import com.monke.user.di.UserModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {
        UserModule.class,
        IdentityModule.class
}
)
@Singleton
public interface AuthComponent {

    void inject(StartFragment fragment);

    void inject(EmailFragment fragment);

    void inject(PasswordFragment fragment);

    void inject(UserInfoFragment fragment);

    @Component.Builder
    interface Builder {
        AuthComponent build();


    }
}
