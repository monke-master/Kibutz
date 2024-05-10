package com.monke.rental.di;

import com.monke.rental.RentalFragment;
import com.monke.rental.RentalModule;
import com.monke.rental.RentalUserListFragment;
import com.monke.rental.newrental.RentalTypeFragment;

import dagger.Component;

@Component(
        modules = {
                RentalModule.class
        },
        dependencies = RentalComponentDeps.class
)
public interface RentalComponent {

    void inject(RentalFragment fragment);
    void inject(RentalUserListFragment fragment);
    void inject(RentalTypeFragment fragment);

    @Component.Builder
    interface Builder {
        Builder setDependencies(RentalComponentDeps deps);

        RentalComponent build();
    }
}
