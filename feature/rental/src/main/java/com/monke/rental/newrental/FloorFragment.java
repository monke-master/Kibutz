package com.monke.rental.newrental;

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

import com.monke.rental.R;
import com.monke.rental.databinding.FragmentFloorBinding;
import com.monke.rental.di.RentalComponentProvider;
import com.monke.ui.TextChangedListener;
import com.monke.utils.StringsHelper;

import javax.inject.Inject;

public class FloorFragment extends Fragment {

    private FloorViewModel mViewModel;
    private FragmentFloorBinding mBinding;

    @Inject
    public FloorViewModel.Factory factory;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        RentalComponentProvider.getInstance().inject(this);
        mViewModel = new ViewModelProvider(this, factory).get(FloorViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentFloorBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initEditTextFloorCount();
        initEditTextFloor();
        initNextBtn();
    }

    private void initEditTextFloorCount() {
        mBinding.editTxtCount.getEditText().setText(
                StringsHelper.getIntOrEmpty(mViewModel.getFloorUiState().getFloorCount()));
        mBinding.editTxtCount.getEditText().addTextChangedListener(new TextChangedListener() {
            @Override
            public void onTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    mViewModel.getFloorUiState().setFloorCount(Integer.parseInt(s.toString()));
                }
            }
        });
    }

    private void initEditTextFloor() {
        mBinding.editTxtFloor.getEditText().setText(
                StringsHelper.getIntOrEmpty(mViewModel.getFloorUiState().getFloor()));
        mBinding.editTxtFloor.getEditText().addTextChangedListener(new TextChangedListener() {
            @Override
            public void onTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    mViewModel.getFloorUiState().setFloor(Integer.parseInt(s.toString()));
                }
            }
        });
    }

    private void initNextBtn() {
        mBinding.btnNext.setOnClickListener(v -> {
            mViewModel.saveData();
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.action_floorFragment_to_flatmatesFragment);
        });
    }
}