package com.monke.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.navigation.Navigator;
import com.example.navigation.RentalNavigationContract;
import com.example.navigation.StartNavigationContract;
import com.monke.profile.databinding.FragmentProfileBinding;
import com.monke.profile.di.ProfileComponentProvider;
import com.monke.ui.rental.RentalRWAdapter;
import com.monke.utils.StringsHelper;

import java.util.stream.Collectors;

import javax.inject.Inject;

public class ProfileFragment extends Fragment {

    @Inject
    public ProfileViewModel.Factory factory;

    @Inject
    public Navigator navigator;

    private ProfileViewModel mViewModel;
    private FragmentProfileBinding mBinding;
    private RentalRWAdapter rentalRWAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        var component = ProfileComponentProvider.getInstance();
        component.inject(this);
        mViewModel = new ViewModelProvider(this, factory).get(ProfileViewModel.class);
        mViewModel.init();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentProfileBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initEditProfileBtn();
        initResponsesRecyclerView();
        fillUserInfo();
        observeRentalsList();
        initSignUpBtn();
    }

    private void fillUserInfo() {
        mViewModel.user.observe(getViewLifecycleOwner(), user -> {
            mBinding.txtName.setText(user.getName() + ", " + StringsHelper.getAge(user.getDateOfBirth()));
            if (!user.getProfile().getPhotosUrl().isEmpty()) {
                Glide
                    .with(getContext())
                    .load(user.getProfile().getPhotosUrl().get(0))
                    .circleCrop()
                    .into(mBinding.image);
            }
        });
    }

    private void observeRentalsList() {
        mViewModel.rentals.observe(getViewLifecycleOwner(), rentalRWAdapter::setRentalList);
    }

    private void initEditProfileBtn() {
        mBinding.btnEditProfile.setOnClickListener(v -> {
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.action_profileFragment_to_editProfileFragment);
        });
    }

    private void initResponsesRecyclerView() {
        RecyclerView recyclerView = mBinding.listRental;
        rentalRWAdapter = new RentalRWAdapter();
        rentalRWAdapter.setShowRespondBtn(false);
        rentalRWAdapter.setOnItemClickListener(rental -> {
            NavHostFragment
                    .findNavController(this)
                    .navigate(RentalNavigationContract.createDeepLinkRequest(rental.getId()));
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(rentalRWAdapter);
    }

    private void initSignUpBtn() {
        mBinding.btnSignOut.setOnClickListener(v -> {
            mViewModel.signOut();
            navigator.closeMainFragment();
        });
    }
}