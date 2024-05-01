package com.monke.kibutz;

import android.app.Application;

import com.monke.utils.StringProvider;
import com.yandex.mapkit.MapKitFactory;

public class KibutzApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MapKitFactory.setApiKey("43cfc245-e9c5-42d5-b13b-1df20b465d9f");
        StringProvider.init(this);
    }
}
