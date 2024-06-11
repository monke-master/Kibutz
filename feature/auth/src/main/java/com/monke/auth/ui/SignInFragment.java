package com.monke.auth.ui;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDeepLinkRequest;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.navigation.MainNavigationContract;
import com.example.navigation.Navigator;
import com.monke.auth.R;
import com.monke.auth.databinding.FragmentSignInBinding;
import com.monke.auth.di.AuthComponent;
import com.monke.auth.di.AuthComponentProvider;
import com.monke.data.UiStatusState;
import com.monke.ui.TextChangedListener;
import com.monke.ui.dialogs.LoadingDialog;

import javax.inject.Inject;

public class SignInFragment extends Fragment {

    @Inject
    public SignInViewModel.Factory factory;

    private FragmentSignInBinding mBinding;
    private SignInViewModel mViewModel;
    private DialogFragment loadingDialog;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        AuthComponentProvider.component.inject(this);
        mViewModel = new ViewModelProvider(this, factory).get(SignInViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentSignInBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initToolbar();
        initEmailEditText();
        initPasswordEditText();
        initNextBtn();
        observeUiStatus();
    }

    private void initToolbar() {
        mBinding.toolbar.setNavigationOnClickListener(v ->
                NavHostFragment.findNavController(this).navigateUp());
    }

    private void initEmailEditText() {
        mBinding.editTxtEmailLayout.getEditText().setText(mViewModel.uiState.getEmail());
        mBinding.editTxtEmailLayout.getEditText().addTextChangedListener(new TextChangedListener() {
            @Override
            public void onTextChanged(Editable s) {
                mViewModel.uiState.setEmail(s.toString());
            }
        });
    }

    private void initPasswordEditText() {
        mBinding.editTxtPassword.getEditText().setText(mViewModel.uiState.getPassword());
        mBinding.editTxtPassword.getEditText().addTextChangedListener(new TextChangedListener() {
            @Override
            public void onTextChanged(Editable s) {
                mViewModel.uiState.setPassword(s.toString());
            }
        });
    }

    private void initNextBtn() {
        mBinding.btnNext.setOnClickListener(v -> {
            mViewModel.signIn();
            loadingDialog = new LoadingDialog();
            loadingDialog.show(getChildFragmentManager(), LoadingDialog.TAG);
        });
    }

    private void observeUiStatus() {
        mViewModel.getUiStatusState().observe(getViewLifecycleOwner(), uiStatusState -> {
            if (uiStatusState.isSuccess()) {
                var controller = NavHostFragment.findNavController(this);
                var navOptions = Navigator.DEFAULT_OPTIONS
                        .setPopUpTo(controller.getGraph().getStartDestinationId(), true)
                        .build();
                controller.navigate(MainNavigationContract.createDeepLink(), navOptions);
                AuthComponentProvider.clear();
            } else if (uiStatusState.isFailure()) {
                loadingDialog.dismiss();
                Toast.makeText(getContext(), uiStatusState.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}