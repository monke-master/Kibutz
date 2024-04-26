package com.monke.auth.ui;

import androidx.lifecycle.ViewModel;

import com.monke.auth.di.AuthComponentProvider;

public class StartViewModel extends ViewModel {

    public void init() {
        AuthComponentProvider.initialize();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}