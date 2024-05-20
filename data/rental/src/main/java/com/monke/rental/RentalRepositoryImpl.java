package com.monke.rental;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.monke.data.FilesRepository;
import com.monke.data.OnCompleteListener;
import com.monke.data.Result;
import com.monke.di.AppScope;
import com.monke.identity.Identity;
import com.monke.identity.IdentityRepository;
import com.monke.user.Mocks;
import com.monke.utils.ThreadUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

@AppScope
public class RentalRepositoryImpl implements RentalRepository {

    private final String TAG = "RentalRepositoryImpl";
    private final RentalCacheDataSource cacheSource;
    private final RentalRemoteDataSource remoteDataSource;
    private final FilesRepository filesRepository;
    private final IdentityRepository identityRepository;
    private final String FILES_COLLECTION = "rentals";

    private ArrayList<Rental> rentals = new ArrayList<>();

    @Inject
    public RentalRepositoryImpl(RentalCacheDataSource cacheSource,
                                RentalRemoteDataSource remoteDataSource,
                                FilesRepository filesRepository,
                                IdentityRepository identityRepository) {
        this.cacheSource = cacheSource;
        this.remoteDataSource = remoteDataSource;
        this.filesRepository = filesRepository;
        this.identityRepository = identityRepository;
        rentals.add(Mocks.mockRental);
        rentals.add(Mocks.mockRental2);
    }

    @Override
    public void saveCreatingRental(Rental rental) {
        cacheSource.saveCreatingRental(rental);
    }

    @Override
    public Rental getCreatingRental() {
        return cacheSource.getCreatingRental();
    }

    @Override
    public LiveData<Result<?>> publishRental(Rental rental) {
        rentals.add(rental);
        MutableLiveData<Result<?>> result = new MutableLiveData<>();
        filesRepository.uploadImages(FILES_COLLECTION, rental.getPhotos(), uploadRes -> {
            if (uploadRes.isFailure()) {
                result.setValue(uploadRes);
                return;
            }
            remoteDataSource.publishRental(new RentalRemote(rental), result::setValue);
        });

        return result;
    }

    @Override
    public void updateRental(Rental rental) {
        Log.d(TAG, rental.toString());
    }

    @Override
    public LiveData<Result<Rental>> getRentalById(String id) {
        var result = new MutableLiveData<Result<Rental>>();
        getRentalById(id, result::setValue);
        return result;
    }

    @Override
    public ArrayList<Rental> getRentals() {
        return rentals;
    }

    @Override
    public LiveData<Result<List<Rental>>> getUserRentals(List<String> rentalIds) {
        MutableLiveData<Result<List<Rental>>> result = new MutableLiveData<>();
        ArrayList<Rental> rentals = new ArrayList<>();
        if (rentalIds.isEmpty()) {
            result.setValue(new Result.Success<>(Collections.emptyList()));
        }
        for (String rentalId: rentalIds) {
            getRentalById(rentalId, rentalRes -> {
                if (rentalRes.isFailure()) {
                    result.setValue(new Result.Failure<>(rentalRes.getException()));
                    return;
                }

                rentals.add(rentalRes.get());
                if (rentals.size() == rentalIds.size()) {
                    result.setValue(new Result.Success<>(rentals));
                }
            });
        }

        return result;
    }

    @Override
    public void getRentalById(String rentalId, OnCompleteListener<Result<Rental>> listener) {
        ThreadUtils.runOnBackground(() -> {
            remoteDataSource.getRentalById(rentalId, rentalResult -> {
                // On error
                if (rentalResult.isFailure()) {
                    listener.onComplete(new Result.Failure<>(rentalResult.getException()));
                    return;
                }

                var rentalRemote = rentalResult.get();

                var paths = rentalRemote
                            .photos
                        .stream()
                        .map(url -> FILES_COLLECTION + "/" + url)
                        .collect(Collectors.toList());
                filesRepository.getFilesDownloadUrls(paths, filesResult -> {
                    if (filesResult.isFailure()) {
                        listener.onComplete(new Result.Failure<>(rentalResult.getException()));
                        return;
                    }

                    rentalRemote.photos = filesResult.get();

                    // Get identities data
                    ArrayList<Identity> identities = new ArrayList<>();
                    for (String identityId: rentalRemote.identityFilters) {
                        identityRepository.getIdentityById(identityId).ifPresent(identities::add);
                    }

                    var rental = rentalRemote.toDomain(identities);
                    rentals.add(rental);
                    listener.onComplete(new Result.Success<>(rental));
                });
            });
        });
    }
}
