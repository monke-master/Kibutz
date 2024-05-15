package com.monke.rental.newrental;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monke.rental.Constants;
import com.monke.rental.databinding.FragmentRoomsBinding;
import com.monke.rental.di.RentalComponentProvider;
import com.monke.ui.TextChipAdapter;

import javax.inject.Inject;

public class RoomsFragment extends Fragment {

    private FragmentRoomsBinding mBinding;
    private RoomsViewModel mViewModel;

    @Inject
    public RoomsViewModel.Factory factory;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        RentalComponentProvider.getInstance().inject(this);
        mViewModel = new ViewModelProvider(this, factory).get(RoomsViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentRoomsBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initChipsGroup();
    }

    private void initChipsGroup() {
        var adapter = new TextChipAdapter(mBinding.chipsRoom, getLayoutInflater());
        adapter.setOnChipSelectedListener(index -> {
            mViewModel.saveRoomsCount(index);
        });
        adapter.bind(
                com.monke.ui.R.string.studio,
                com.monke.ui.R.plurals.rooms,
                Constants.MAX_ROOMS,
                com.monke.ui.R.layout.item_chip_button);

    }


}