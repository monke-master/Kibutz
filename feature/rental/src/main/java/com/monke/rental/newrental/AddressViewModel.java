package com.monke.rental.newrental;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.map.GetAddressByCoordinatesUseCase;
import com.monke.data.Result;
import com.monke.rental.CreateRentalUseCase;
import com.monke.user.PublishRentalUseCase;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.search.Address;

import java.util.concurrent.Executors;

import javax.inject.Inject;

public class AddressViewModel extends ViewModel {


    private GetAddressByCoordinatesUseCase getAddressByCoordinatesUseCase;

    public AddressViewModel(CreateRentalUseCase createRentalUseCase,
                            GetAddressByCoordinatesUseCase getAddressByCoordinatesUseCase) {
        this.getAddressByCoordinatesUseCase = getAddressByCoordinatesUseCase;
    }


    public void getAddress(Point point) {

        getAddressByCoordinatesUseCase.execute(point);

//        observeForever(addressResult -> {
//
//            Log.d("GetAddressByCoordinatesUseCase", ((Result.Success<String>)addressResult).getData());
//        });


    }


    public static class Factory implements ViewModelProvider.Factory {

        private final CreateRentalUseCase createRentalUseCase;
        private final GetAddressByCoordinatesUseCase getAddressByCoordinatesUseCase;


        @Inject
        public Factory(CreateRentalUseCase createRentalUseCase,
                       GetAddressByCoordinatesUseCase getAddressByCoordinatesUseCase) {
            this.createRentalUseCase = createRentalUseCase;
            this.getAddressByCoordinatesUseCase = getAddressByCoordinatesUseCase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) (new AddressViewModel(createRentalUseCase, getAddressByCoordinatesUseCase));
        }
    }
}