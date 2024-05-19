package com.monke.user;

import com.monke.rental.Rental;
import com.monke.rental.RentalRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
        User user = userRepository.getCurrentUser().getValue();
        Rental rental = rentalRepository.getCreatingRental();
        rental.setAuthorId(user.getId());
        rental.setCreationDate(Calendar.getInstance().getTimeInMillis());
        rental.setFlatmatesIds(List.of(user.getId()));
        rentalRepository.publishRental(rental);

        ArrayList<Rental> rentalList = new ArrayList<>(user.getRentals());
        rentalList.add(rental);
        user.setRentals(rentalList);

        userRepository.saveUser(user);
    }
}
