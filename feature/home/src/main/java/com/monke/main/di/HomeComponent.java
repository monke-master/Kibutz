package com.monke.main.di;

import com.monke.di.AppScope;
import com.monke.firebase.RemoteModule;
import com.monke.identity.di.IdentityModule;
import com.monke.main.HomeFragment;
import com.monke.rental.RentalModule;

import dagger.Component;

@Component(
        dependencies = HomeComponentDeps.class,
        modules = {
                RentalModule.class,
                RemoteModule.class,
                IdentityModule.class
        }
)
@AppScope
public interface HomeComponent {

    void inject(HomeFragment fragment);


    @Component.Builder
    interface Builder {

        HomeComponent build();

        Builder setDependencies(HomeComponentDeps deps);
    }
}
