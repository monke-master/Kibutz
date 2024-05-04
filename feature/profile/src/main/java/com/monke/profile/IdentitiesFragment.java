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

import com.monke.identity.Identity;
import com.monke.profile.databinding.FragmentIdentitiesBinding;
import com.monke.profile.di.ProfileComponentProvider;

import javax.inject.Inject;

public class IdentitiesFragment extends Fragment {

    private FragmentIdentitiesBinding mBinding;

    private IdentitiesViewModel mViewModel;

    @Inject
    public IdentitiesViewModel.Factory factory;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ProfileComponentProvider.getInstance().inject(this);
        mViewModel = new ViewModelProvider(this, factory).get(IdentitiesViewModel.class);

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
        mViewModel.identities.observe(getViewLifecycleOwner(), identities -> {
            for (Identity identity: identities) {

            }
        });
    }
}