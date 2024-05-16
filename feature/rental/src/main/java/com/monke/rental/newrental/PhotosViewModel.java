package com.monke.rental.newrental;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.rental.SaveRentalUseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PhotosViewModel extends ViewModel {

    private final SaveRentalUseCase saveRentalUseCase;

    private final MutableLiveData<List<String>> _photos = new MutableLiveData<>(new ArrayList<>());
    public LiveData<List<String>> photos = _photos;

    public PhotosViewModel(SaveRentalUseCase saveRentalUseCase) {
        this.saveRentalUseCase = saveRentalUseCase;
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
        saveRentalUseCase.savePhotos(_photos.getValue());
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
            return (T) (new PhotosViewModel(saveRentalUseCase));
        }
    }
}