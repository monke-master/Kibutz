package com.monke.profile.di;

import com.example.navigation.Navigator;
import com.monke.user.UserRepository;

public interface ProfileComponentDeps {
    UserRepository getUserRepository();

    Navigator getNavigator();
}
