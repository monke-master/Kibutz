package com.monke.user;

import com.monke.rental.Rental;
import com.monke.rental.Response;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class GetUsersFromResponsesUseCase {

    private final UserRepository userRepository;

    @Inject
    public GetUsersFromResponsesUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> execute(Rental rental) {
        ArrayList<User> result = new ArrayList<>();
        for (Response response: rental.getResponses()) {
            userRepository.getUserById(response.getUserId()).ifPresent(result::add);
        }
        return result;
    }
}
