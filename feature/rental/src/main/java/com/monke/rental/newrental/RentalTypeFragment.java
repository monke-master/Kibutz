package com.monke.rental.newrental;

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

import com.monke.rental.R;
import com.monke.rental.RentalViewModel;
import com.monke.rental.databinding.FragmentRentalTypeBinding;
import com.monke.rental.di.RentalComponentProvider;

import javax.inject.Inject;

public class RentalTypeFragment extends Fragment {

    private FragmentRentalTypeBinding mBinding;
    private RentalTypeViewModel mViewModel;

    @Inject
    public RentalTypeViewModel.Factory factory;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        RentalComponentProvider.getInstance().inject(this);
        mViewModel = new ViewModelProvider(this, factory).get(RentalTypeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentRentalTypeBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBinding.btnFlat.setOnClickListener(v -> {
            mViewModel.setRentalType(true);
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_rentalTypeFragment_to_descriptionFragment);
        });

        mBinding.btnHouse.setOnClickListener(v -> {
            mViewModel.setRentalType(false);
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_rentalTypeFragment_to_descriptionFragment);
        });
    }
}