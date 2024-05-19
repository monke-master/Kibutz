package com.monke.rental.di;

import com.example.map.MapModule;
import com.monke.di.RentalScope;
import com.monke.rental.RentalFragment;
import com.monke.rental.RentalModule;
import com.monke.rental.RentalUserListFragment;
import com.monke.rental.ResponsesFragment;
import com.monke.rental.newrental.AddressFragment;
import com.monke.rental.newrental.AreaFragment;
import com.monke.rental.newrental.ContactsFragment;
import com.monke.rental.newrental.DescriptionFragment;
import com.monke.rental.newrental.FlatmatesFragment;
import com.monke.rental.newrental.FloorFragment;
import com.monke.rental.newrental.PhotosFragment;
import com.monke.rental.newrental.PriceFragment;
import com.monke.rental.newrental.RentalTypeFragment;
import com.monke.rental.newrental.RoomsFragment;
import com.monke.rental.newrental.SearchAddressFragment;

import dagger.Component;

@Component(
        modules = {
                RentalModule.class,
                MapModule.class
        },
        dependencies = RentalComponentDeps.class
)
@RentalScope
public interface RentalComponent {

    void inject(RentalFragment fragment);
    void inject(RentalUserListFragment fragment);
    void inject(RentalTypeFragment fragment);
    void inject(AddressFragment fragment);
    void inject(SearchAddressFragment fragment);
    void inject(RoomsFragment fragment);
    void inject(AreaFragment fragment);
    void inject(FloorFragment fragment);
    void inject(FlatmatesFragment fragment);
    void inject(PhotosFragment fragment);
    void inject(PriceFragment fragment);
    void inject(DescriptionFragment fragment);
    void inject(ContactsFragment fragment);
    void inject(ResponsesFragment fragment);

    @Component.Builder
    interface Builder {
        Builder setDependencies(RentalComponentDeps deps);

        RentalComponent build();
    }
}
