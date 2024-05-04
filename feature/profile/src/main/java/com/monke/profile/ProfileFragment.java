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

import com.monke.profile.databinding.FragmentProfileBinding;
import com.monke.profile.di.ProfileComponentProvider;

import javax.inject.Inject;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    private FragmentProfileBinding mBinding;

    @Inject
    public ProfileViewModel.Factory factory;

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
        fillUserInfo();
    }

    private void fillUserInfo() {
        mViewModel.user.observe(getViewLifecycleOwner(), user -> {
            mBinding.txtName.setText(user.getName());
        });
    }

    private void initEditProfileBtn() {
        mBinding.btnEditProfile.setOnClickListener(v -> {
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.action_profileFragment_to_editProfileFragment);
        });
    }
}