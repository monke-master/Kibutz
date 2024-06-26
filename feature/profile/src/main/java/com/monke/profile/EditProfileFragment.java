package com.monke.profile;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.navigation.PickIdentitiesNavigationContract;
import com.monke.identity.Identity;
import com.monke.identity.IdentityModel;
import com.monke.profile.databinding.FragmentEditProfileBinding;
import com.monke.profile.di.ProfileComponentProvider;
import com.monke.ui.DimensionsHelper;
import com.monke.ui.chips.IdentityChipAdapter;
import com.monke.ui.dialogs.LoadingDialog;
import com.monke.ui.photo.PhotoRWAdapter;
import com.monke.ui.TextChangedListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class EditProfileFragment extends Fragment {

    private EditProfileViewModel mViewModel;
    private FragmentEditProfileBinding mBinding;
    private IdentityChipAdapter mIdentityChipAdapter;
    private PhotoRWAdapter mPictureAdapter;
    private DialogFragment loadingDialog;

    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                if (uri != null) {
                    mViewModel.addPhoto(uri.toString());
                }
            });

    @Inject
    public EditProfileViewModel.Factory factory;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        var component = ProfileComponentProvider.getInstance();
        component.inject(this);
        mViewModel = new ViewModelProvider(this, factory).get(EditProfileViewModel.class);
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

        mIdentityChipAdapter = new IdentityChipAdapter(mBinding.chips, getLayoutInflater());
        initToolbar();
        fillUserInfo();
        initEditTextBio();
        initAddIdentityChip();
        setFragmentResultListener();
        observeIdentities();
        initPhotosRecyclerView();
        initSaveButton();
        observePhotos();
        observeUiStatusState();
    }

    private void initToolbar() {
        mBinding.toolbar.setNavigationOnClickListener(v -> NavHostFragment.findNavController(this).navigateUp());
    }

    private void setFragmentResultListener() {
        getParentFragmentManager().setFragmentResultListener(PickIdentitiesNavigationContract.RESULT_KEY,
                getViewLifecycleOwner(), (requestKey, result) -> {
                    List<Identity> identities =
                            result.getParcelableArrayList(PickIdentitiesNavigationContract.IDENTITIES_KEY)
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
            Bundle bundle = new Bundle();
            bundle.putStringArray(
                    PickIdentitiesNavigationContract.IDENTITIES_TYPES_KEY,
                    new String[]{ Identity.Type.POSITIVE.name() }
            );
            bundle.putStringArray(
                    PickIdentitiesNavigationContract.UNAVAILABLE_IDS_KEY,
                    mViewModel.getIdentitiesIds().toArray(new String[0])
            );
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.action_editProfileFragment_to_identitiesFragment, bundle);
        });
    }

    private void observeIdentities() {
        mViewModel.identities.observe(getViewLifecycleOwner(), identities -> {
            mIdentityChipAdapter.bindFromLast(identities, false);
        });
    }

    private void initPhotosRecyclerView() {
        mPictureAdapter = new PhotoRWAdapter(
                DimensionsHelper.getDp(com.monke.ui.R.dimen.profile_image_width),
                DimensionsHelper.getDp(com.monke.ui.R.dimen.profile_image_height));
        mPictureAdapter.setAddButtonClickedListener(() -> {
            pickMedia.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build());
        });
        mPictureAdapter.setOnRemovePhotoListener(pos -> {
            mViewModel.removePhoto(pos);
        });

        mBinding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mBinding.recyclerView.setAdapter(mPictureAdapter);
    }

    private void initSaveButton() {
        mBinding.btnSave.setOnClickListener(v -> {
            mViewModel.save();
            loadingDialog = new LoadingDialog();
            loadingDialog.show(getChildFragmentManager(), LoadingDialog.TAG);
        });
    }

    private void observePhotos() {
        mViewModel.photos.observe(getViewLifecycleOwner(), photos -> {
            mPictureAdapter.setPhotoUris(photos);
        });
    }

    private void observeUiStatusState() {
        mViewModel.getUiStatusState().observe(getViewLifecycleOwner(), uiStatusState -> {
            if (uiStatusState.isSuccess()) {
                NavHostFragment.findNavController(this).navigateUp();

            } else if (uiStatusState.isFailure()){
                loadingDialog.dismiss();
                Toast.makeText(getContext(), uiStatusState.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}