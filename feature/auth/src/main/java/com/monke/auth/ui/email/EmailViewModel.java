package com.monke.auth.ui.email;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.user.SaveEmailUseCase;
import com.monke.user.SendConfirmationLetterUseCase;

import javax.inject.Inject;

public class EmailViewModel extends ViewModel {

    private final SaveEmailUseCase saveEmailUseCase;
    private final SendConfirmationLetterUseCase sendConfirmationLetterUseCase;

    private MutableLiveData<Boolean> _emailConfirmed = new MutableLiveData<>();
    public LiveData<Boolean> emailConfirmed = _emailConfirmed;

    private String email;

    public EmailViewModel(SaveEmailUseCase saveEmailUseCase,
                          SendConfirmationLetterUseCase sendConfirmationLetterUseCase) {
        this.saveEmailUseCase = saveEmailUseCase;
        this.sendConfirmationLetterUseCase = sendConfirmationLetterUseCase;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void verifyEmail() {
        sendConfirmationLetterUseCase.execute(email).observeForever(result -> {
            if (result.isSuccess()) {
                _emailConfirmed.setValue(result.get());
            }
        });
    }

    public void saveData() {
        saveEmailUseCase.execute(email);
    }

    public LiveData<Boolean> getEmailConfirmed() {
        return emailConfirmed;
    }

    public static class Factory implements ViewModelProvider.Factory {

        private final SaveEmailUseCase saveEmailUseCase;
        private final SendConfirmationLetterUseCase sendConfirmationLetterUseCase;

        @Inject
        public Factory(SaveEmailUseCase saveEmailUseCase,
                       SendConfirmationLetterUseCase sendConfirmationLetterUseCase) {
            this.saveEmailUseCase = saveEmailUseCase;
            this.sendConfirmationLetterUseCase = sendConfirmationLetterUseCase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) (new EmailViewModel(saveEmailUseCase, sendConfirmationLetterUseCase));
        }
    }

}
