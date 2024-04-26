package com.monke.auth.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.user.SaveEmailUseCase;

import javax.inject.Inject;

public class EmailViewModel extends ViewModel {

    private final SaveEmailUseCase saveEmailUseCase;

    private String email;

    public EmailViewModel(SaveEmailUseCase saveEmailUseCase) {
        this.saveEmailUseCase = saveEmailUseCase;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void verifyEmail() {

    }

    public static class Factory implements ViewModelProvider.Factory {

        private final SaveEmailUseCase saveEmailUseCase;

        @Inject
        public Factory(SaveEmailUseCase saveEmailUseCase) {
            this.saveEmailUseCase = saveEmailUseCase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) (new EmailViewModel(saveEmailUseCase));
        }
    }

}
