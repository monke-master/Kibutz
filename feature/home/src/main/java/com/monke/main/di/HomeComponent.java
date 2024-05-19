package com.monke.main.di;

import com.monke.main.HomeFragment;

import dagger.Component;

@Component(
        dependencies = HomeComponentDeps.class
)
public interface HomeComponent {

    void inject(HomeFragment fragment);


    @Component.Builder
    interface Builder {

        HomeComponent build();

        Builder setDependencies(HomeComponentDeps deps);
    }
}
