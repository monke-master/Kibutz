package com.example.map;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.monke.data.Result;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.search.Address;
import com.yandex.mapkit.search.Response;
import com.yandex.mapkit.search.SearchManager;
import com.yandex.mapkit.search.SearchOptions;
import com.yandex.mapkit.search.Session;
import com.yandex.runtime.Error;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class GetAddressByCoordinatesUseCase {

    private SearchManager searchManager;

    @Inject
    public GetAddressByCoordinatesUseCase(SearchManager searchManager) {
        this.searchManager = searchManager;
    }

    public LiveData<Result<String>> execute(Point point) {
        MutableLiveData<Result<String>> result = new MutableLiveData<>();
        Log.d("GetAddressByCoordinatesUseCase", Thread.currentThread().getName());
        searchManager.submit(
                point,
                0,
                new SearchOptions(),
                new Session.SearchListener() {
                    @Override
                    public void onSearchResponse(@NonNull Response response) {
                        var address = response.getCollection().getChildren().get(0).getObj().getName();
                        result.setValue(new Result.Success<>(address));
                    }

                    @Override
                    public void onSearchError(@NonNull Error error) {
                        result.setValue(new Result.Failure<>(new Exception()));
                    }
                }
        );
        return result;
    }
}
