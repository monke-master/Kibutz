package com.monke.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

public class HomeViewModel extends ViewModel {



    public static class Factory implements ViewModelProvider.Factory {



        @Inject
        public Factory() {

        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) (new HomeViewModel());
        }
    }
}