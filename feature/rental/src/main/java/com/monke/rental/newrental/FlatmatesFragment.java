package com.monke.rental.newrental;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monke.identity.Identity;
import com.monke.rental.R;
import com.monke.rental.databinding.FragmentFlatmatesBinding;
import com.monke.rental.di.RentalComponentProvider;
import com.monke.ui.IdentityChipAdapter;
import com.monke.ui.TextChangedListener;
import com.monke.utils.StringsHelper;

import java.util.List;

import javax.inject.Inject;

public class FlatmatesFragment extends Fragment {

    private FlatmatesViewModel mViewModel;
    private FragmentFlatmatesBinding mBinding;

    @Inject
    public FlatmatesViewModel.Factory factory;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        RentalComponentProvider.getInstance().inject(this);
        mViewModel = new ViewModelProvider(this, factory).get(FlatmatesViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentFlatmatesBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initEditTextFlatmatesCount();
    }

    private void initEditTextFlatmatesCount() {
        mBinding.editTxtCount.getEditText().setText(
                StringsHelper.getIntOrEmpty(mViewModel.getUiState().getFlatmatesCount()));
        mBinding.editTxtCount.getEditText().addTextChangedListener(new TextChangedListener() {
            @Override
            public void onTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    mViewModel.getUiState().setFlatmatesCount(Integer.parseInt(s.toString()));
                }
            }
        });
    }

    private void initFiltersChipGroup() {
//        IdentityChipAdapter adapter = new IdentityChipAdapter(mBinding.chipsReqs, getLayoutInflater());
//        adapter.bind(mViewModel.getUiState().getIdentityFilters(), );
    }

}