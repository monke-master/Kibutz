package com.monke.auth.di;

import com.monke.auth.ui.email.EmailFragment;
import com.monke.auth.ui.StartFragment;
import com.monke.auth.ui.password.PasswordFragment;
import com.monke.user.di.AuthScope;
import com.monke.user.di.UserModule;

import dagger.Component;


@Component(modules = { UserModule.class }
)
@AuthScope
public interface AuthComponent {

    void inject(StartFragment fragment);

    void inject(EmailFragment fragment);

    void inject(PasswordFragment fragment);

    @Component.Builder
    interface Builder {
        AuthComponent build();


    }
}
