package com.monke.profile;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monke.profile.databinding.FragmentIdentitiesBinding;
import com.monke.profile.di.ProfileComponentProvider;
import com.monke.ui.IdentityChipAdapter;

import javax.inject.Inject;

public class IdentitiesFragment extends Fragment {

    private FragmentIdentitiesBinding mBinding;
    private IdentitiesViewModel mViewModel;

    @Inject
    public IdentitiesViewModel.Factory factory;
    public static String RESULT_KEY = "CHIPS_RESULT";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ProfileComponentProvider.getInstance().inject(this);
        mViewModel = new ViewModelProvider(this, factory).get(IdentitiesViewModel.class);
        mViewModel.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentIdentitiesBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        observeIdentitiesList();
    }


    private void observeIdentitiesList() {
        IdentityChipAdapter adapter = new IdentityChipAdapter(mBinding.chipsIdentities, getLayoutInflater());
        mViewModel.identities.observe(getViewLifecycleOwner(), adapter::bind);
    }
}