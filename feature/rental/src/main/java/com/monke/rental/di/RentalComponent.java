package com.monke.rental.di;

import com.monke.rental.RentalFragment;
import com.monke.rental.RentalUserListFragment;

import dagger.Component;

@Component(
        dependencies = RentalComponentDeps.class
)
public interface RentalComponent {

    void inject(RentalFragment fragment);
    void inject(RentalUserListFragment fragment);

    @Component.Builder
    interface Builder {
        Builder setDependencies(RentalComponentDeps deps);

        RentalComponent build();
    }
}
