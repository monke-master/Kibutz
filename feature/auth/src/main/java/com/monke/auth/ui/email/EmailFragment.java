package com.monke.auth.ui.email;

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

import com.monke.auth.R;
import com.monke.auth.databinding.FragmentEmailBinding;
import com.monke.auth.di.AuthComponentProvider;
import com.monke.ui.TextChangedListener;

import javax.inject.Inject;

public class EmailFragment extends Fragment {

    @Inject
    public EmailViewModel.Factory mViewModelfactory;
    private EmailViewModel mViewModel;

    private FragmentEmailBinding mBinding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        AuthComponentProvider.component.inject(this);
        mViewModel = new ViewModelProvider(this, mViewModelfactory).get(EmailViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentEmailBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initToolbar();
        initEmailEditText();
        initNextBtn();
    }

    private void initToolbar() {
        mBinding.toolbar.setNavigationOnClickListener(v ->
                NavHostFragment.findNavController(this).navigateUp());
    }

    private void initEmailEditText() {
        mBinding.editTxtEmail.setText(mViewModel.getEmail());
        mBinding.editTxtEmail.addTextChangedListener(new TextChangedListener() {
            @Override
            public void onTextChanged(Editable s) {
                mViewModel.setEmail(s.toString());
            }
        });
    }

    private void initNextBtn() {
        mBinding.btnNext.setOnClickListener(v -> {
            mViewModel.verifyEmail();
            observeConfirmationStatus();
            var dialog = new EmailConfirmationDialog();
            dialog.show(getChildFragmentManager(), "EmailConfirmationDialog");
        });
    }

    private void observeConfirmationStatus() {
        mViewModel.emailConfirmed.observe(getViewLifecycleOwner(), confirmed -> {
            if (confirmed) {
                mViewModel.saveData();
                NavHostFragment
                        .findNavController(this)
                        .navigate(R.id.action_emailFragment_to_passwordFragment);
            }
        });
    }
}