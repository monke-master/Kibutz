package com.monke.main;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monke.main.databinding.FragmentHomeBinding;
import com.monke.main.di.HomeComponentProvider;

import javax.inject.Inject;

public class HomeFragment extends Fragment {

    @Inject
    public HomeViewModel.Factory factory;

    private HomeViewModel mViewModel;
    private FragmentHomeBinding mBinding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        HomeComponentProvider.getInstance().inject(this);
        mViewModel = new ViewModelProvider(this, factory).get(HomeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }




}