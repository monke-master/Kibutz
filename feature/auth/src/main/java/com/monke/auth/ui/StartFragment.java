package com.monke.auth.ui;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monke.auth.R;
import com.monke.auth.databinding.FragmentStartBinding;

public class StartFragment extends Fragment {

    private StartViewModel mViewModel;
    private FragmentStartBinding mBinding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mViewModel = new ViewModelProvider(this).get(StartViewModel.class);
        mViewModel.init();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentStartBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initSignUpBtn();
        initSignInBtn();
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
}