package com.monke.profile;

import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monke.profile.databinding.FragmentEditProfileBinding;
import com.monke.profile.di.ProfileComponentProvider;
import com.monke.ui.TextChangedListener;

import javax.inject.Inject;

public class EditProfileFragment extends Fragment {

    private EditProfileViewModel mViewModel;
    private FragmentEditProfileBinding mBinding;

    @Inject
    public EditProfileViewModel.Factory factory;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        var component = ProfileComponentProvider.getInstance();
        component.inject(this);
        mViewModel = new ViewModelProvider(this, factory).get(EditProfileViewModel.class);
        mViewModel.init();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentEditProfileBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fillUserInfo();
        initEditTextBio();
        initAddIdentityChip();

        getParentFragmentManager().setFragmentResultListener(IdentitiesFragment.RESULT_KEY,
                getViewLifecycleOwner(), (requestKey, result) -> {

        });
    }

    private void fillUserInfo() {
        mViewModel.user.observe(getViewLifecycleOwner(), user -> {
            mBinding.editTxtBio.getEditText().setText(user.getProfile().getBio());
        });
    }

    private void initEditTextBio() {
        mBinding.editTxtBio.getEditText().setText(mViewModel.uiState.getBio());
        mBinding.editTxtBio.getEditText().addTextChangedListener(new TextChangedListener() {
            @Override
            public void onTextChanged(Editable s) {
                mViewModel.uiState.setBio(s.toString());
            }
        });
    }

    private void initAddIdentityChip() {
        mBinding.chipAdd.setOnClickListener(v -> {
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.action_editProfileFragment_to_identitiesFragment);
        });
    }
}