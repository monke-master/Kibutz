package com.monke.rental.newrental;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.rental.CreateRentalUseCase;
import com.monke.rental.GetCreatingRentalUseCase;
import com.monke.rental.Rental;

import javax.inject.Inject;

public class RoomsViewModel extends ViewModel {

    private final CreateRentalUseCase createRentalUseCase;
    private final GetCreatingRentalUseCase getCreatingRentalUseCase;

    private Rental rental;

    public RoomsViewModel(CreateRentalUseCase createRentalUseCase,
                          GetCreatingRentalUseCase getCreatingRentalUseCase) {
        this.createRentalUseCase = createRentalUseCase;
        this.getCreatingRentalUseCase = getCreatingRentalUseCase;
        rental = getCreatingRentalUseCase.execute();
    }

    public void saveRoomsCount(int count) {
        createRentalUseCase.saveRoomsCount(count);
    }

    public boolean isFlat() {
        return rental.getRealty().isFlat();
    }

    public static class Factory implements ViewModelProvider.Factory {

        private final CreateRentalUseCase createRentalUseCase;
        private final GetCreatingRentalUseCase getCreatingRentalUseCase;

        @Inject
        public Factory(CreateRentalUseCase createRentalUseCase,
                       GetCreatingRentalUseCase getCreatingRentalUseCase) {
            this.createRentalUseCase = createRentalUseCase;
            this.getCreatingRentalUseCase = getCreatingRentalUseCase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) (new RoomsViewModel(createRentalUseCase, getCreatingRentalUseCase));
        }
    }
}