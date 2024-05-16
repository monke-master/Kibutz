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
import com.monke.rental.databinding.FragmentPriceBinding;
import com.monke.rental.di.RentalComponentProvider;
import com.monke.ui.TextChangedListener;
import com.monke.utils.StringsHelper;

import javax.inject.Inject;

public class PriceFragment extends Fragment {

    @Inject
    public PriceViewModel.Factory factory;

    private PriceViewModel mViewModel;
    private FragmentPriceBinding mBinding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        RentalComponentProvider.getInstance().inject(this);
        mViewModel = new ViewModelProvider(this, factory).get(PriceViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentPriceBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initEditTextPrice();
        initNextBtn();
    }

    private void initEditTextPrice() {
        mBinding.editTxtPrice.getEditText().setText(
                StringsHelper.getLongOrEmpty(mViewModel.getPriceUiState().getPrice()));
        mBinding.editTxtPrice.getEditText().addTextChangedListener(new TextChangedListener() {
            @Override
            public void onTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    mViewModel.getPriceUiState().setPrice(Long.parseLong(s.toString()));
                }
            }
        });
    }

    private void initNextBtn() {
        mBinding.btnNext.setOnClickListener(v -> {
            mViewModel.saveData();
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.action_priceFragment_to_descriptionFragment);
        });
    }
}