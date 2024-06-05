package com.monke.main;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.navigation.Navigator;
import com.monke.main.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;

    private FragmentMainBinding mBinding;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentMainBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        Navigator.setMainFragment(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavHostFragment navHostFragment =
                (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.fragment_container);

        NavController navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(mBinding.bottomNavigationView, navController);
        // mBinding.bottomNavigationView.setItemIconTintList(null);
    }
}