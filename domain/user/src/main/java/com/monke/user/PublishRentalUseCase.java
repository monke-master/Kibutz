package com.monke.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.monke.data.Result;
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

    public LiveData<Result<?>> execute() {
        MutableLiveData<Result<?>> result = new MutableLiveData<>();

        User user = userRepository.getCurrentUser().getValue();
        Rental rental = rentalRepository.getCreatingRental();
        rental.setAuthorId(user.getId());
        rental.setCreationDate(Calendar.getInstance().getTimeInMillis());
        rental.setFlatmatesIds(List.of(user.getId()));
        rentalRepository.publishRental(rental).observeForever(rentalRes -> {
            if (rentalRes.isFailure()) {
                result.setValue(rentalRes);
                return;
            }
            ArrayList<Rental> rentalList = new ArrayList<>(user.getRentals());
            rentalList.add(rental);
            user.setRentals(rentalList);

            userRepository.setCurrentUser(user);

            result.setValue(new Result.Success<>());
        });

        return result;
    }
}
