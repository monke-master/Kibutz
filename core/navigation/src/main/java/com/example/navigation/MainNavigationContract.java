package com.example.navigation;

import android.net.Uri;

import androidx.navigation.NavDeepLinkRequest;
import androidx.navigation.fragment.NavHostFragment;

public class MainNavigationContract {

    public static NavDeepLinkRequest createDeepLink() {
        return NavDeepLinkRequest.Builder
                .fromUri(Uri.parse("kibutz://com.monke.app/main_fragment"))
                .build();
    }
}
