package com.monke.rental.newrental;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.rental.CreateRentalUseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PhotosViewModel extends ViewModel {

    private final CreateRentalUseCase createRentalUseCase;

    private final MutableLiveData<List<String>> _photos = new MutableLiveData<>(new ArrayList<>());
    public LiveData<List<String>> photos = _photos;

    public PhotosViewModel(CreateRentalUseCase createRentalUseCase) {
        this.createRentalUseCase = createRentalUseCase;
    }

    public void addPhoto(String uri) {
        var list = new ArrayList<>(_photos.getValue());
        list.add(uri);
        _photos.setValue(list);
    }

    public void removePhoto(int index) {
        var list = new ArrayList<>(_photos.getValue());
        list.remove(index);
        _photos.setValue(list);
    }

    public void saveData() {
        createRentalUseCase.savePhotos(_photos.getValue());
    }

    public static class Factory implements ViewModelProvider.Factory {

        private final CreateRentalUseCase createRentalUseCase;

        @Inject
        public Factory(CreateRentalUseCase createRentalUseCase) {
            this.createRentalUseCase = createRentalUseCase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) (new PhotosViewModel(createRentalUseCase));
        }
    }
}