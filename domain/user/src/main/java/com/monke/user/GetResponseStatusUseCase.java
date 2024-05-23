package com.monke.user;

import android.util.Log;

import androidx.annotation.Nullable;

import com.monke.rental.Rental;
import com.monke.rental.Response;

import javax.inject.Inject;

public class GetResponseStatusUseCase {

    @Inject
    public GetResponseStatusUseCase() {}

    @Nullable
    public Response.Status execute(User user, Rental rental) {
        for (Response response: rental.getResponses()) {
            if (response.getUserId().equals(user.getId()))  {
                Log.d("GetResponseStatusUseCase", response.toString());
                return response.getStatus();
            }
        }

        return null;
    }
}
