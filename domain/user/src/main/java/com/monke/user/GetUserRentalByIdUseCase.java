package com.monke.user;

import com.monke.rental.Rental;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class GetUserRentalByIdUseCase {

    private final UserRepository userRepository;

    @Inject
    public GetUserRentalByIdUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Rental execute(String id) {
        return userRepository
                .getCurrentUser()
                .getValue()
                .getRentals()
                .stream()
                .filter(rental -> Objects.equals(rental.getId(), id))
                .collect(Collectors.toList()).get(0);
    }
}
