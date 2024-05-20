package com.monke.rental.newrental;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.data.UiStatusState;
import com.monke.rental.CreateRentalUseCase;
import com.monke.user.PublishRentalUseCase;

import javax.inject.Inject;

public class ContactsViewModel extends ViewModel {

    private ContactsUiState contactsUiState = new ContactsUiState();
    private CreateRentalUseCase createRentalUseCase;
    private PublishRentalUseCase publishRentalUseCase;

    private final MutableLiveData<UiStatusState> _uiStatusState =
            new MutableLiveData<>(new UiStatusState.Default());
    private final LiveData<UiStatusState> uiStatusState = _uiStatusState;

    private final String TAG = "ContactsViewModel";

    public ContactsViewModel(CreateRentalUseCase createRentalUseCase,
                             PublishRentalUseCase publishRentalUseCase) {
        this.createRentalUseCase = createRentalUseCase;
        this.publishRentalUseCase = publishRentalUseCase;
    }

    public ContactsUiState getContactsUiState() {
        return contactsUiState;
    }

    public void saveData() {
        createRentalUseCase.saveContacts(
                contactsUiState.getEmail(),
                contactsUiState.getPhone(),
                contactsUiState.getTelegram()
        );
        publishRentalUseCase.execute().observeForever(result -> {
            Log.d(TAG, result.toString());
            _uiStatusState.setValue(UiStatusState.fromResult(result));
        });
    }

    public LiveData<UiStatusState> getUiStatusState() {
        return uiStatusState;
    }

    public static class Factory implements ViewModelProvider.Factory {

        private final CreateRentalUseCase createRentalUseCase;
        private final PublishRentalUseCase publishRentalUseCase;


        @Inject
        public Factory(CreateRentalUseCase createRentalUseCase,
                       PublishRentalUseCase publishRentalUseCase) {
            this.createRentalUseCase = createRentalUseCase;
            this.publishRentalUseCase = publishRentalUseCase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) (new ContactsViewModel(createRentalUseCase, publishRentalUseCase));
        }
    }
}