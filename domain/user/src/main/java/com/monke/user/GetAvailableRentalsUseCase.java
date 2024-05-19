package com.monke.user;

import com.monke.data.Result;
import com.monke.identity.Identity;
import com.monke.rental.Rental;
import com.monke.rental.RentalRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class GetAvailableRentalsUseCase {

    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;

    @Inject
    public GetAvailableRentalsUseCase(UserRepository userRepository, RentalRepository rentalRepository) {
        this.userRepository = userRepository;
        this.rentalRepository = rentalRepository;
    }

    public Result<List<Rental>> execute() {
        User user = userRepository.getCurrentUser().getValue();
        var result = new ArrayList<Rental>();

        var rentals = rentalRepository.getRentals();
        RENTAL_LOOP: for (Rental rental: rentals) {
            for (Identity identity: user.getProfile().getIdentities()) {
                var filters = rental
                                .getIdentityFilters()
                                .stream()
                                .map(Identity::getId)
                                .collect(Collectors.toList());
                if (filters.contains(identity.getOppositeId())) {
                    continue RENTAL_LOOP;
                }
            }
            result.add(rental);
        }

        return new Result.Success<>(result);
    }
}
