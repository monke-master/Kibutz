package com.monke.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.monke.data.Result;
import com.monke.rental.Rental;
import com.monke.rental.RentalRepository;
import com.monke.rental.Response;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

public class RespondToRentalUseCase {

    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;

    @Inject
    public RespondToRentalUseCase(UserRepository userRepository, RentalRepository rentalRepository ) {
        this.userRepository = userRepository;
        this.rentalRepository = rentalRepository;
    }

    public LiveData<Result<?>> execute(Rental rental) {
        var result = new MutableLiveData<Result<?>>();
        User user = userRepository.getCurrentUser().getValue().clone();
        Response response = new Response(
                UUID.randomUUID().toString(),
                user.getId(),
                rental.getId(),
                Calendar.getInstance().getTimeInMillis(),
                Response.Status.HANGING
        );

        List<Response> userResponses = new ArrayList<>(user.getResponses());
        userResponses.add(response);
        user.setResponses(userResponses);

        var updatedRental = rental.clone();
        List<Response> rentalResponses = new ArrayList<>(updatedRental.getResponses());
        rentalResponses.add(response);
        updatedRental.setResponses(rentalResponses);

        rentalRepository.uploadResponse(response, uploadingRes -> {
            if (uploadingRes.isFailure()) {
                result.setValue(uploadingRes);
                return;
            }

            rentalRepository.updateRental(rental, updatedRental).observeForever(rentalRes -> {
                if (rentalRes.isFailure()) {
                    result.setValue(rentalRes);
                    return;
                }

                userRepository.setCurrentUser(user).observeForever(result::setValue);
            });
        });


        return result;
    }
}
