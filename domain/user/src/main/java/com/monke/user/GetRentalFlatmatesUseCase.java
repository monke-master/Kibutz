package com.monke.user;

import com.monke.rental.Rental;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class GetRentalFlatmatesUseCase {

    private final UserRepository userRepository;
    private final GetUserByIdUseCase getUserByIdUseCase;

    @Inject
    public GetRentalFlatmatesUseCase(UserRepository userRepository,
                                     GetUserByIdUseCase getUserByIdUseCase) {
        this.userRepository = userRepository;
        this.getUserByIdUseCase = getUserByIdUseCase;
    }

    public List<User> execute(Rental rental) {
        ArrayList<User> users = new ArrayList<>();

        for (String id: rental.getFlatmatesIds()) {
            getUserByIdUseCase.execute(id).ifPresent(users::add);
        }

        return users;
    }
}
