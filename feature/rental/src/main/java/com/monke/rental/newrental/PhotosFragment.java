package com.monke.rental.newrental;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monke.rental.R;
import com.monke.rental.databinding.FragmentPhotosBinding;
import com.monke.rental.di.RentalComponentProvider;
import com.monke.ui.DimensionsHelper;
import com.monke.ui.photo.PhotoRWAdapter;

import javax.inject.Inject;

public class PhotosFragment extends Fragment {

    @Inject
    public PhotosViewModel.Factory factory;

    private PhotosViewModel mViewModel;
    private FragmentPhotosBinding mBinding;
    private PhotoRWAdapter adapter;

    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                if (uri != null) {
                    mViewModel.addPhoto(uri.toString());
                }
            });

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        RentalComponentProvider.getInstance().inject(this);
        mViewModel = new ViewModelProvider(this, factory).get(PhotosViewModel.class);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentPhotosBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initPhotosRW();
        observePhotos();
        initNextBtn();
    }

    private void initPhotosRW() {
        adapter = new PhotoRWAdapter(
                DimensionsHelper.getDp(com.monke.ui.R.dimen.creating_rental_photo_size),
                DimensionsHelper.getDp(com.monke.ui.R.dimen.creating_rental_photo_size));

        adapter.setAddButtonClickedListener(() -> {
            pickMedia.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build());
        });

        adapter.setOnRemovePhotoListener(index -> {
            mViewModel.removePhoto(index);
        });

        RecyclerView photoRW = mBinding.recyclerView;
        photoRW.setLayoutManager(new GridLayoutManager(getContext(), 2));
        photoRW.setAdapter(adapter);
    }

    private void observePhotos() {
        mViewModel.photos.observe(getViewLifecycleOwner(), photos -> {
            adapter.setPhotoUris(photos);
        });
    }

    private void initNextBtn() {
        mBinding.btnNext.setOnClickListener(v -> {
            mViewModel.saveData();
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.action_photosFragment_to_priceFragment);
        });
    }

}