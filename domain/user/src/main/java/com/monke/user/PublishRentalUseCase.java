package com.monke.user;

import com.monke.rental.Rental;
import com.monke.rental.RentalRepository;

import java.util.ArrayList;

import javax.inject.Inject;

public class PublishRentalUseCase {
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;

    @Inject
    public PublishRentalUseCase(UserRepository userRepository, RentalRepository rentalRepository) {
        this.userRepository = userRepository;
        this.rentalRepository = rentalRepository;
    }

    public void execute() {
        Rental rental = rentalRepository.getCreatingRental();
        rentalRepository.publishRental(rental);

        User user = userRepository.getCurrentUser();
        ArrayList<Rental> rentalList = new ArrayList<>(user.getRentals());
        rentalList.add(rental);
        user.setRentals(rentalList);

        userRepository.saveUser(user);
    }
}
