package com.monke.rental;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monke.rental.databinding.FragmentRentalBinding;
import com.monke.rental.di.RentalComponentProvider;

import javax.inject.Inject;

public class RentalFragment extends Fragment {

    private RentalViewModel mViewModel;
    private FragmentRentalBinding mBinding;

    @Inject
    public RentalViewModel.Factory factory;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        RentalComponentProvider.getInstance().inject(this);
        mViewModel = new ViewModelProvider(this, factory).get(RentalViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentRentalBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

}