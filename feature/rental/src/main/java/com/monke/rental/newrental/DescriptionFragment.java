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
import com.monke.rental.databinding.FragmentDescriptionBinding;
import com.monke.rental.di.RentalComponentProvider;
import com.monke.ui.TextChangedListener;

import javax.inject.Inject;

public class DescriptionFragment extends Fragment {

    @Inject
    public DescriptionViewModel.Factory factory;

    private DescriptionViewModel mViewModel;
    private FragmentDescriptionBinding mBinding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        RentalComponentProvider.getInstance().inject(this);
        mViewModel = new ViewModelProvider(this, factory).get(DescriptionViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentDescriptionBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initEditTextDescription();
        initNextBtn();
    }

    public void initEditTextDescription() {
        mBinding.editTxtDescription.getEditText().setText(mViewModel.getDescription());
        mBinding.editTxtDescription.getEditText().addTextChangedListener(new TextChangedListener() {
            @Override
            public void onTextChanged(Editable s) {
                mViewModel.setDescription(s.toString());
            }
        });
    }

    public void initNextBtn() {
        mBinding.btnNext.setOnClickListener(v -> {
            mViewModel.saveData();
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.action_descriptionFragment_to_contactsFragment);
        });
    }
}