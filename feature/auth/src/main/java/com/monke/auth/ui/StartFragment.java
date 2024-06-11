package com.monke.auth.ui;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.navigation.MainNavigationContract;
import com.example.navigation.Navigator;
import com.monke.auth.R;
import com.monke.auth.databinding.FragmentStartBinding;
import com.monke.auth.di.AuthComponentProvider;

import javax.inject.Inject;

public class StartFragment extends Fragment {

    @Inject
    public StartViewModel.Factory factory;

    private StartViewModel mViewModel;
    private FragmentStartBinding mBinding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        AuthComponentProvider.initialize();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentStartBinding.inflate(inflater, container, false);
        AuthComponentProvider.component.inject(this);
        mViewModel = new ViewModelProvider(this, factory).get(StartViewModel.class);
        mViewModel.init();
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initSignUpBtn();
        initSignInBtn();
        observeAuthStatus();
    }

    private void initSignUpBtn() {
        mBinding.btnSignUp.setOnClickListener(v ->
                NavHostFragment
                        .findNavController(this)
                        .navigate(R.id.action_startFragment_to_emailFragment)
        );
    }

    private void initSignInBtn() {
        mBinding.btnSignIn.setOnClickListener(v ->
                NavHostFragment
                        .findNavController(this)
                        .navigate(R.id.action_startFragment_to_signInFragment)
        );
    }

    private void observeAuthStatus() {
        mViewModel.authenticated.observe(getViewLifecycleOwner(), authenticated -> {
            if (authenticated) {
                var controller = NavHostFragment.findNavController(this);
                var navOptions = Navigator.DEFAULT_OPTIONS
                        .setPopUpTo(controller.getGraph().getStartDestinationId(), true)
                        .build();
                controller.navigate(MainNavigationContract.createDeepLink(), navOptions);
            }
        });
    }
}