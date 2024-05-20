package com.monke.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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

    public LiveData<Result<List<Rental>>> execute() {
        User user = userRepository.getCurrentUser().getValue();

        return rentalRepository.getAvailableRentals(user.getId());
    }
}
