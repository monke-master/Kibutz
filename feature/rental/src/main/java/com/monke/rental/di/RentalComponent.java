package com.monke.rental.di;

import com.monke.rental.RentalFragment;

import dagger.Component;

@Component(
        dependencies = RentalComponentDeps.class
)
public interface RentalComponent {

    void inject(RentalFragment fragment);

    @Component.Builder
    interface Builder {
        Builder setDependencies(RentalComponentDeps deps);

        RentalComponent build();
    }
}
