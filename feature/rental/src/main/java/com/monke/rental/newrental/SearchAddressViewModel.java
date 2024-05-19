package com.monke.rental.newrental;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.map.GetAddressByCoordinatesUseCase;
import com.example.map.SearchAddressUseCase;
import com.monke.rental.Address;
import com.monke.rental.CreateRentalUseCase;
import com.monke.utils.ThreadUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class SearchAddressViewModel extends ViewModel {

    private final CreateRentalUseCase createRentalUseCase;
    private final SearchAddressUseCase searchAddressUseCase;

    private MutableLiveData<List<Address>> _result = new MutableLiveData<>();
    public LiveData<List<Address>> result = _result;

    private ExecutorService executorService;
    private final long SEARCHING_DELAY = 1000l;

    public SearchAddressViewModel(CreateRentalUseCase createRentalUseCase,
                                  SearchAddressUseCase searchAddressUseCase) {
        this.createRentalUseCase = createRentalUseCase;
        this.searchAddressUseCase = searchAddressUseCase;
    }

    public void searchAddress(String query) {
        if (executorService != null) {
            executorService.shutdownNow();
        }

        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                Thread.sleep(SEARCHING_DELAY);
                ThreadUtils.postOnUiThread(() -> search(query));
            } catch (InterruptedException e) {

            }
        });
    }

    public void saveResult(Address address) {
        createRentalUseCase.saveAddress(address.getDescription());
    }

    private void search(String query) {
        searchAddressUseCase.execute(query).observeForever(result -> {
            if (result.isSuccess()) {
                _result.setValue(result.get());
            }
        });
    }

    public static class Factory implements ViewModelProvider.Factory {

        private final CreateRentalUseCase createRentalUseCase;
        private final SearchAddressUseCase searchAddressUseCase;


        @Inject
        public Factory(CreateRentalUseCase createRentalUseCase,
                       SearchAddressUseCase searchAddressUseCase) {
            this.createRentalUseCase = createRentalUseCase;
            this.searchAddressUseCase = searchAddressUseCase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) (new SearchAddressViewModel(createRentalUseCase, searchAddressUseCase));
        }
    }
}