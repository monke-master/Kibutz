package com.monke.user;

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

    public void execute(Rental rental) {
        User user = userRepository.getCurrentUser().getValue();
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

        List<Response> rentalResponses = new ArrayList<>(rental.getResponses());
        rentalResponses.add(response);
        rental.setResponses(rentalResponses);

        userRepository.setCurrentUser(user);
        rentalRepository.updateRental(rental, rental);
    }
}
