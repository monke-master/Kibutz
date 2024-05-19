package com.monke.main;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monke.main.databinding.FragmentHomeBinding;
import com.monke.main.di.HomeComponentProvider;
import com.monke.ui.rental.RentalRWAdapter;

import javax.inject.Inject;

public class HomeFragment extends Fragment {

    @Inject
    public HomeViewModel.Factory factory;

    private HomeViewModel mViewModel;
    private FragmentHomeBinding mBinding;

    private RentalRWAdapter mAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        HomeComponentProvider.getInstance().inject(this);
        mViewModel = new ViewModelProvider(this, factory).get(HomeViewModel.class);
        mViewModel.init();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRecyclerView();
        observeLiveData();
    }

    private void initRecyclerView() {
        var recyclerView = mBinding.listRental;

        mAdapter = new RentalRWAdapter();
        mAdapter.setType(RentalRWAdapter.Type.SMALL);
        mAdapter.setOnItemClickListener(v -> {

        });

        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);
    }

    private void observeLiveData() {
        mViewModel.rentals.observe(getViewLifecycleOwner(), mAdapter::setRentalList);
    }
}