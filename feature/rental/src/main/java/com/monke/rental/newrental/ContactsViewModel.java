package com.monke.rental.newrental;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.rental.SaveRentalUseCase;

import javax.inject.Inject;

public class ContactsViewModel extends ViewModel {

    private ContactsUiState contactsUiState = new ContactsUiState();
    private SaveRentalUseCase saveRentalUseCase;

    public ContactsViewModel(SaveRentalUseCase saveRentalUseCase) {
        this.saveRentalUseCase = saveRentalUseCase;
    }

    public ContactsUiState getContactsUiState() {
        return contactsUiState;
    }

    public void saveData() {
        saveRentalUseCase.saveContacts(
                contactsUiState.getEmail(),
                contactsUiState.getPhone(),
                contactsUiState.getTelegram()
        );
    }

    public static class Factory implements ViewModelProvider.Factory {

        private final SaveRentalUseCase saveRentalUseCase;

        @Inject
        public Factory(SaveRentalUseCase saveRentalUseCase) {
            this.saveRentalUseCase = saveRentalUseCase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) (new ContactsViewModel(saveRentalUseCase));
        }
    }
}