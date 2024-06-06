package com.monke.main;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.navigation.RentalNavigationContract;
import com.monke.main.databinding.FragmentHomeBinding;
import com.monke.main.di.HomeComponentProvider;
import com.monke.ui.DimensionsHelper;
import com.monke.ui.GridSpacingItemDecoration;
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
        mAdapter.setOnItemClickListener(rental -> {
            NavHostFragment
                    .findNavController(this)
                    .navigate(RentalNavigationContract.createDeepLinkRequest(rental.getId()));
        });
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(
                (int) getResources().getDimension(com.monke.ui.R.dimen.grid_layout_vertical_padding),
                2,
                (int) getResources().getDimension(com.monke.ui.R.dimen.rental_small_image_width)));

        recyclerView.setLayoutManager(
                new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(mAdapter);
    }

    private void observeLiveData() {
        mViewModel.rentals.observe(getViewLifecycleOwner(), mAdapter::setRentalList);
    }
}