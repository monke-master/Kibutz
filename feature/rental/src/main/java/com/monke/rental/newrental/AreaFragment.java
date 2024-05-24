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
import com.monke.rental.databinding.FragmentAreaBinding;
import com.monke.rental.di.RentalComponentProvider;
import com.monke.ui.TextChangedListener;
import com.monke.utils.StringsHelper;

import javax.inject.Inject;

public class AreaFragment extends Fragment {

    private AreaViewModel mViewModel;
    private FragmentAreaBinding mBinding;

    @Inject
    public AreaViewModel.Factory factory;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        RentalComponentProvider.getInstance().inject(this);
        mViewModel = new ViewModelProvider(this, factory).get(AreaViewModel.class);
        mViewModel.init();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentAreaBinding.inflate(getLayoutInflater(), container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initToolbar();
        initEditTextArea();
        initNextButton();
        if (mViewModel.isFlat()) {
            bindAsFlatData();
        } else {
            bindAsHomeData();
        }

    }

    private void initToolbar() {
        mBinding.toolbar.setNavigationOnClickListener(v ->
                NavHostFragment.findNavController(this).navigateUp()
        );
    }

    private void initEditTextArea() {
        mBinding.editTxtArea.getEditText().setText(
                StringsHelper.getFloatOrEmpty(mViewModel.getAreaUiState().getArea()));
        mBinding.editTxtArea.getEditText().addTextChangedListener(new TextChangedListener() {
            @Override
            public void onTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    mViewModel.getAreaUiState().setArea(Float.parseFloat(s.toString()));
                }
            }
        });
    }

    private void initNextButton() {
        mBinding.btnNext.setOnClickListener(v -> {
            mViewModel.saveData();
            NavHostFragment.findNavController(this).navigate(R.id.action_areaFragment_to_floorFragment);
        });
    }

    private void bindAsFlatData() {
        mBinding.editTxtLivingArea.getEditText().setText(
                StringsHelper.getFloatOrEmpty(mViewModel.getAreaUiState().getLivingArea()));
        mBinding.editTxtLivingArea.getEditText().addTextChangedListener(new TextChangedListener() {
            @Override
            public void onTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    mViewModel.getAreaUiState().setLivingArea(Float.parseFloat(s.toString()));
                }
            }
        });

        mBinding.editTxtKitchen.getEditText().setText(
                StringsHelper.getFloatOrEmpty(mViewModel.getAreaUiState().getKitchenArea()));
        mBinding.editTxtKitchen.getEditText().addTextChangedListener(new TextChangedListener() {
            @Override
            public void onTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    mViewModel.getAreaUiState().setKitchenArea(Float.parseFloat(s.toString()));
                }
            }
        });
    }

    private void bindAsHomeData() {
        mBinding.hdr.setText(com.monke.ui.R.string.house_area);
        mBinding.editTxtArea.setHint(com.monke.ui.R.string.house_area);

        mBinding.editTxtLivingArea.setHint(com.monke.ui.R.string.plot_area);
        mBinding.editTxtLivingArea.getEditText().setText(
                StringsHelper.getFloatOrEmpty(mViewModel.getAreaUiState().getPlotArea())
        );
        mBinding.editTxtLivingArea.getEditText().addTextChangedListener(new TextChangedListener() {
            @Override
            public void onTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    mViewModel.getAreaUiState().setPlotArea(Float.parseFloat(s.toString()));
                }
            }
        });

        mBinding.editTxtKitchen.setVisibility(View.GONE);
    }
}