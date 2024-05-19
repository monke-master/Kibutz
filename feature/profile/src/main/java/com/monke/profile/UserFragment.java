package com.monke.profile;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.navigation.PhotoNavigationContract;
import com.example.navigation.UserNavigationContract;
import com.monke.profile.databinding.FragmentUserBinding;
import com.monke.profile.di.ProfileComponentProvider;
import com.monke.ui.chips.IdentityChipAdapter;
import com.monke.ui.photo.PhotoPagerAdapter;

import javax.inject.Inject;

public class UserFragment extends Fragment {

    @Inject
    public UserViewModel.Factory factory;

    private UserViewModel mViewModel;
    private FragmentUserBinding mBinding;
    private IdentityChipAdapter mIdentityChipAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ProfileComponentProvider.getInstance().inject(this);
        mViewModel = new ViewModelProvider(this, factory).get(UserViewModel.class);
        getArgs();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentUserBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initToolbar();
        initChipsAdapter();
        observeUser();
    }

    private void initToolbar() {
        mBinding.toolbar.setNavigationOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigateUp();
        });
    }

    private void initChipsAdapter() {
        mIdentityChipAdapter = new IdentityChipAdapter(mBinding.chips, getLayoutInflater());
    }

    private void observeUser() {
        mViewModel.user.observe(getViewLifecycleOwner(), user -> {

            PhotoPagerAdapter photoPagerAdapter = new PhotoPagerAdapter(
                    user.getProfile().getPhotosUrl(),
                    getContext(),
                    com.monke.ui.R.layout.item_user_photo
            );
            photoPagerAdapter.setOnPhotoClickedListener(uri -> {
                NavHostFragment
                        .findNavController(this)
                        .navigate(PhotoNavigationContract.createDeepLinkRequest(uri));
            });
            mBinding.image.setAdapter(photoPagerAdapter);

            mBinding.txtName.setText(user.getName());
            mBinding.txtAboutMe.setText(user.getProfile().getBio());

            mIdentityChipAdapter.bind(user.getProfile().getIdentities(), false);
        });
    }

    private void getArgs() {
        String userId = getArguments().getString(UserNavigationContract.USER_ID_KEY);
        mViewModel.init(userId);
    }
}