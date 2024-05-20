package com.monke.user;

import com.monke.rental.RentalRepository;
import com.monke.rental.Response;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ChangeResponseStatusUseCase {

    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;

    @Inject
    public ChangeResponseStatusUseCase(UserRepository userRepository,
                                       RentalRepository rentalRepository) {
        this.userRepository = userRepository;
        this.rentalRepository = rentalRepository;
    }

    public Response execute(Response response, Response.Status newStatus) {
        User user = userRepository.getLocalUserById(response.getUserId()).get();
        List<Response> userResponses = new ArrayList<>(user.getResponses());
        userResponses.remove(response);

//        Rental rental = rentalRepository.getRentalById(response.getRentalId());
//        List<Response> rentalResponses = new ArrayList<>(rental.getResponses());
//        rentalResponses.remove(response);

        Response newResponse = new Response(response);
        newResponse.setStatus(newStatus);

        userResponses.add(newResponse);
        user.setResponses(userResponses);

//        rentalResponses.add(newResponse);
//        rental.setResponses(rentalResponses);

        userRepository.setCreatingUser(user);
        // rentalRepository.updateRental(rental);
        return newResponse;
    }
}
