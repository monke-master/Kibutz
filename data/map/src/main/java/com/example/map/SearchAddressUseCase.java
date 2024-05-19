package com.example.map;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.monke.data.Result;
import com.monke.rental.Address;
import com.yandex.mapkit.geometry.Geometry;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.search.Response;
import com.yandex.mapkit.search.SearchManager;
import com.yandex.mapkit.search.SearchOptions;
import com.yandex.mapkit.search.Session;
import com.yandex.runtime.Error;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class SearchAddressUseCase {
    private SearchManager searchManager;

    @Inject
    public SearchAddressUseCase(SearchManager searchManager) {
        this.searchManager = searchManager;
    }

    public LiveData<Result<List<Address>>> execute(String query) {
        MutableLiveData<Result<List<Address>>> result = new MutableLiveData<>();
        searchManager.submit(
                query,
                Geometry.fromPoint(new Point(55, 37)),
                new SearchOptions(),
                new Session.SearchListener() {
                    @Override
                    public void onSearchResponse(@NonNull Response response) {
                        var list = response
                                .getCollection()
                                .getChildren()
                                .stream()
                                .map(item -> new Address(item.getObj().getName(), item.getObj().getDescriptionText()))
                                .collect(Collectors.toList());
                        result.setValue(new Result.Success<>(list));
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
