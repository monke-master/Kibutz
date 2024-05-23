package com.example.navigation;

import android.net.Uri;

import androidx.navigation.NavDeepLinkRequest;

public class RentalNavigationContract {
    public static String RENTAL_ID_KEY = "rentalId";

    public static String BASE_URI = "kibutz://com.monke.rental/rental";

    public static NavDeepLinkRequest createDeepLinkRequest(String rentalId) {
        NavDeepLinkRequest request = NavDeepLinkRequest.Builder
                .fromUri(Uri.parse(BASE_URI + "/" + RENTAL_ID_KEY + "=" + rentalId))
                .build();
        return request;
    }
}
