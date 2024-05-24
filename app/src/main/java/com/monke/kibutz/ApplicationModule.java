package com.monke.kibutz;

import com.example.navigation.Navigator;

import dagger.Binds;
import dagger.Module;

@Module
public interface ApplicationModule {

    @Binds
    Navigator bindNavigator(NavigatorImpl i);
}
