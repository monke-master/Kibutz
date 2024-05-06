package com.monke.profile;

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

import com.monke.identity.Identity;
import com.monke.identity.IdentityModel;
import com.monke.profile.databinding.FragmentEditProfileBinding;
import com.monke.profile.di.ProfileComponentProvider;
import com.monke.ui.IdentityChipAdapter;
import com.monke.ui.TextChangedListener;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class EditProfileFragment extends Fragment {

    private EditProfileViewModel mViewModel;
    private FragmentEditProfileBinding mBinding;
    private IdentityChipAdapter mAdapter;

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

        mAdapter = new IdentityChipAdapter(mBinding.chips, getLayoutInflater());
        fillUserInfo();
        initEditTextBio();
        initAddIdentityChip();
        setFragmentResultListener();
        observeIdentities();
        initSaveButton();
    }

    private void setFragmentResultListener() {
        getParentFragmentManager().setFragmentResultListener(IdentitiesFragment.RESULT_KEY,
                getViewLifecycleOwner(), (requestKey, result) -> {
                    List<Identity> identities =
                            result.getParcelableArrayList(IdentitiesFragment.IDENTITIES_KEY)
                                    .stream()
                                    .map(i -> ((IdentityModel)i).getIdentity())
                                    .collect(Collectors.toList());
                    mViewModel.addIdentities(identities);
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

    private void observeIdentities() {
        mViewModel.identities.observe(getViewLifecycleOwner(), identities -> {
            mAdapter.bindFromLast(identities, false);
        });
    }

    private void initSaveButton() {
        mBinding.btnSave.setOnClickListener(v -> {
            mViewModel.save();
            NavHostFragment.findNavController(this).navigateUp();
        });
    }
}