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

import com.monke.rental.R;
import com.monke.rental.databinding.FragmentContactsBinding;
import com.monke.rental.di.RentalComponentProvider;
import com.monke.ui.TextChangedListener;

import javax.inject.Inject;

public class ContactsFragment extends Fragment {

    @Inject
    public ContactsViewModel.Factory factory;

    private FragmentContactsBinding mBinding;
    private ContactsViewModel mViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        RentalComponentProvider.getInstance().inject(this);
        mViewModel = new ViewModelProvider(this, factory).get(ContactsViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentContactsBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initEditTextPhone();
        initEditTextTelegram();
        initEditTextEmail();
        initNextBtn();
    }

    private void initEditTextPhone() {
        mBinding.editTxtPhone.getEditText().setText(mViewModel.getContactsUiState().getPhone());
        mBinding.editTxtPhone.getEditText().addTextChangedListener(new TextChangedListener() {
            @Override
            public void onTextChanged(Editable s) {
                mViewModel.getContactsUiState().setPhone(s.toString());
            }
        });
    }

    private void initEditTextTelegram() {
        mBinding.editTxtTg.getEditText().setText(mViewModel.getContactsUiState().getTelegram());
        mBinding.editTxtTg.getEditText().addTextChangedListener(new TextChangedListener() {
            @Override
            public void onTextChanged(Editable s) {
                mViewModel.getContactsUiState().setTelegram(s.toString());
            }
        });
    }

    private void initEditTextEmail() {
        mBinding.editTxtEmail.getEditText().setText(mViewModel.getContactsUiState().getEmail());
        mBinding.editTxtEmail.getEditText().addTextChangedListener(new TextChangedListener() {
            @Override
            public void onTextChanged(Editable s) {
                mViewModel.getContactsUiState().setEmail(s.toString());
            }
        });
    }

    private void initNextBtn() {
        mBinding.btnNext.setOnClickListener(v -> {
            mViewModel.saveData();

        });
    }
}