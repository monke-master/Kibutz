package com.monke.kibutz;

import android.app.Application;

import com.monke.auth.di.AuthComponentProvider;
import com.monke.profile.di.ProfileComponentProvider;
import com.monke.rental.di.RentalComponentProvider;
import com.monke.ui.DimensionsHelper;
import com.monke.utils.StringProvider;
import com.yandex.mapkit.MapKitFactory;

public class KibutzApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        MapKitFactory.setApiKey("43cfc245-e9c5-42d5-b13b-1df20b465d9f");
        MapKitFactory.initialize(this);
        StringProvider.init(this);
        applicationComponent = DaggerApplicationComponent.builder().build();
        AuthComponentProvider.setDependencies(applicationComponent);
        ProfileComponentProvider.setDependencies(applicationComponent);
        RentalComponentProvider.setDependencies(applicationComponent);
        DimensionsHelper.init(this);
    }
}
