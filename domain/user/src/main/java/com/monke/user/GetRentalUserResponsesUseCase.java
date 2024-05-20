package com.monke.user;

import android.util.Pair;

import com.monke.rental.Rental;
import com.monke.rental.Response;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class GetRentalUserResponsesUseCase {

    private final UserRepository userRepository;

    @Inject
    public GetRentalUserResponsesUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Pair<Response, User>> execute(Rental rental) {
        ArrayList<Pair<Response, User>> result = new ArrayList<>();
        for (Response response: rental.getResponses()) {
            userRepository.getLocalUserById(response.getUserId()).ifPresent(user ->
                    result.add(new Pair<>(response, user)));
        }
        return result;
    }
}
