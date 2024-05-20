package com.monke.auth.di;

import com.monke.auth.ui.SignInFragment;
import com.monke.auth.ui.email.EmailFragment;
import com.monke.auth.ui.StartFragment;
import com.monke.auth.ui.info.UserInfoFragment;
import com.monke.auth.ui.password.PasswordFragment;
import com.monke.di.AuthScope;
import com.monke.firebase.RemoteModule;
import com.monke.identity.di.IdentityModule;

import dagger.Component;

@Component(
        modules = {
                IdentityModule.class,
                RemoteModule.class,
                RemoteModule.class
        },
        dependencies = AuthComponentDeps.class
)
@AuthScope
public interface AuthComponent {

    void inject(StartFragment fragment);
    void inject(EmailFragment fragment);
    void inject(PasswordFragment fragment);
    void inject(UserInfoFragment fragment);
    void inject(SignInFragment fragment);

    @Component.Builder
    interface Builder {
        AuthComponent build();

        Builder setDependencies(AuthComponentDeps deps);
    }
}
