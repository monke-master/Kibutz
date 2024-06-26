package com.monke.auth.ui.password;

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
import com.monke.auth.databinding.FragmentPasswordBinding;
import com.monke.auth.di.AuthComponentProvider;
import com.monke.data.UiStatusState;
import com.monke.ui.TextChangedListener;

import javax.inject.Inject;

public class PasswordFragment extends Fragment {

    private FragmentPasswordBinding mBinding;
    private PasswordViewModel mViewModel;

    @Inject
    public PasswordViewModel.Factory mViewModelFactory;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        AuthComponentProvider.component.inject(this);
        mViewModel = new ViewModelProvider(this, mViewModelFactory).get(PasswordViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentPasswordBinding.inflate(getLayoutInflater(), container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initToolbar();
        initPasswordEditText();
        initRepeatPasswordEditText();
        initNextButton();
        subscribeToUiState();
    }

    private void initToolbar() {
        mBinding.toolbar.setNavigationOnClickListener(v ->
                NavHostFragment.findNavController(this).navigateUp());
    }

    private void initPasswordEditText() {
        mBinding.editTxtPassword.setText(mViewModel.getUiState().getPassword());
        mBinding.editTxtPassword.addTextChangedListener(new TextChangedListener() {
            @Override
            public void onTextChanged(Editable s) {
                mViewModel.getUiState().setPassword(s.toString());
            }
        });
    }

    private void initRepeatPasswordEditText() {
        mBinding.editTxtPassword.setText(mViewModel.getUiState().getRepeatedPassword());
        mBinding.editTxtRepeatPassword.addTextChangedListener(new TextChangedListener() {
            @Override
            public void onTextChanged(Editable s) {
                mViewModel.getUiState().setRepeatedPassword(s.toString());
            }
        });
    }

    private void initNextButton() {
        mBinding.btnNext.setOnClickListener(v -> {
            mViewModel.savePassword();
        });
    }

    private void subscribeToUiState() {
        mViewModel.getUiStatus().observe(getViewLifecycleOwner(), uiStatus -> {
            if (uiStatus.isSuccess()) {
                NavHostFragment
                        .findNavController(this)
                        .navigate(R.id.action_passwordFragment_to_userInfoFragment);
                mViewModel.getUiStatus().removeObservers(getViewLifecycleOwner());
                mViewModel.clearStatus();
            } else if (uiStatus.isFailure()) {
                Exception exception = ((UiStatusState.Error)uiStatus).getException();

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}