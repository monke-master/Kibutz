package com.monke.rental.di;

import com.monke.di.RentalScope;
import com.monke.rental.RentalFragment;
import com.monke.rental.RentalModule;
import com.monke.rental.RentalUserListFragment;
import com.monke.rental.newrental.AreaFragment;
import com.monke.rental.newrental.RentalTypeFragment;
import com.monke.rental.newrental.RoomsFragment;

import dagger.Component;

@Component(
        modules = {
                RentalModule.class
        },
        dependencies = RentalComponentDeps.class
)
@RentalScope
public interface RentalComponent {

    void inject(RentalFragment fragment);
    void inject(RentalUserListFragment fragment);
    void inject(RentalTypeFragment fragment);
    void inject(RoomsFragment fragment);
    void inject(AreaFragment fragment);

    @Component.Builder
    interface Builder {
        Builder setDependencies(RentalComponentDeps deps);

        RentalComponent build();
    }
}
