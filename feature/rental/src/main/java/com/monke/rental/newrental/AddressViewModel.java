package com.monke.rental.newrental;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.map.GetAddressByCoordinatesUseCase;
import com.monke.data.Result;
import com.monke.rental.CreateRentalUseCase;
import com.monke.user.PublishRentalUseCase;
import com.monke.utils.ThreadUtils;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.search.Address;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class AddressViewModel extends ViewModel {


    private GetAddressByCoordinatesUseCase getAddressByCoordinatesUseCase;
    private CreateRentalUseCase createRentalUseCase;
    private ExecutorService addressSearchingExecutor;

    private MutableLiveData<String> _address = new MutableLiveData<>();
    public LiveData<String> address = _address;

    private final long SEARCHING_DELAY = 1000L;

    public AddressViewModel(CreateRentalUseCase createRentalUseCase,
                            GetAddressByCoordinatesUseCase getAddressByCoordinatesUseCase) {
        this.createRentalUseCase = createRentalUseCase;
        this.getAddressByCoordinatesUseCase = getAddressByCoordinatesUseCase;
    }

    public void getAddress(Point point) {
        addressSearchingExecutor = Executors.newSingleThreadExecutor();
        addressSearchingExecutor.execute(() -> {
            try {
                Log.d("GetAddressByCoordinatesUseCase", Thread.currentThread().getName());
                Thread.sleep(SEARCHING_DELAY);
                Log.d("GetAddressByCoordinatesUseCase", Thread.currentThread().getName());
                ThreadUtils.postOnUiThread(() -> observeAddress(point));
            } catch (InterruptedException e) {

            }

        });
    }

    public void stopSearchingProcess() {
        if (addressSearchingExecutor != null) {
            addressSearchingExecutor.shutdownNow();
        }
    }

    public void saveAddress() {
        createRentalUseCase.saveAddress(address.getValue());
    }

    private void observeAddress(Point point) {
        getAddressByCoordinatesUseCase.execute(point).observeForever(addressResult -> {
            if (addressResult.isSuccess()) {
                _address.setValue(addressResult.get());
            }
        });
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