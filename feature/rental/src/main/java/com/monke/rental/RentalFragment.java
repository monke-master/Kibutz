package com.monke.rental;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.navigation.RentalFragmentContract;
import com.monke.rental.databinding.FragmentRentalBinding;
import com.monke.rental.di.RentalComponentProvider;
import com.monke.ui.FlatmateRWAdapter;

import javax.inject.Inject;

public class RentalFragment extends Fragment {

    private RentalViewModel mViewModel;
    private FragmentRentalBinding mBinding;
    private FlatmateRWAdapter flatmateRWAdapter;

    @Inject
    public RentalViewModel.Factory factory;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        RentalComponentProvider.getInstance().inject(this);
        mViewModel = new ViewModelProvider(this, factory).get(RentalViewModel.class);
        getArgs();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentRentalBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initFlatmateAdapter();
        observeRental();
        observeFlatmates();
    }

    private void getArgs() {
        String rentalId = getArguments().getString(RentalFragmentContract.RENTAL_ID_KEY);
        mViewModel.setRentalId(rentalId);
    }

    private void initFlatmateAdapter() {
        flatmateRWAdapter = new FlatmateRWAdapter();
        var recyclerView =  mBinding.flatmates.listFlatmates;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(flatmateRWAdapter);
        flatmateRWAdapter.setOnItemClickedListener(user -> {

        });
    }

    private void observeRental() {
        mViewModel.rental.observe(getViewLifecycleOwner(), rental -> {
            Glide
                .with(getContext())
                .load(rental.getPhotos().get(0))
                .centerCrop()
                .into(mBinding.image);
            mBinding.txtPrice.setText(getString(com.monke.ui.R.string.price_info, rental.getPrice()));
            mBinding.txtDescription.setText(rental.getDescription());
            var info = mBinding.info;
            info.txtRoomCount.setText(
                    getResources().getQuantityString(
                            com.monke.ui.R.plurals.rooms,
                            rental.getRealty().getRoomsCount(),
                            rental.getRealty().getRoomsCount())
            );
            info.txtType.setText(com.monke.ui.R.string.flat);
            info.txtArea.setText(getString(com.monke.ui.R.string.area_info, rental.getRealty().getArea()));
            if (rental.getRealty() instanceof Flat) {
                bindFlatInfo((Flat) rental.getRealty());
            }


        });
    }

    private void bindFlatInfo(Flat flat) {
        mBinding.info.txtFloor.setText(
                getString(com.monke.ui.R.string.floors_info, flat.getFloor(), flat.getFloorsCount())
        );
    }

    private void observeFlatmates() {
        mViewModel.flatmates.observe(getViewLifecycleOwner(), users -> {
            flatmateRWAdapter.setUsersList(users);
        });
    }
}