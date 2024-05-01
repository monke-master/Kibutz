package com.monke.auth.ui.info;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.auth.ui.email.EmailViewModel;
import com.monke.user.SaveEmailUseCase;

import javax.inject.Inject;

public class UserInfoViewModel extends ViewModel {



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