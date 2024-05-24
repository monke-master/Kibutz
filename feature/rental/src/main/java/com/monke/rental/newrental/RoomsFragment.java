package com.monke.rental.newrental;

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

import com.monke.rental.Constants;
import com.monke.rental.R;
import com.monke.rental.databinding.FragmentRoomsBinding;
import com.monke.rental.di.RentalComponentProvider;
import com.monke.ui.chips.TextChipAdapter;

import javax.inject.Inject;

public class RoomsFragment extends Fragment {

    private FragmentRoomsBinding mBinding;
    private RoomsViewModel mViewModel;
    private TextChipAdapter adapter;

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

        initToolbar();
        adapter = new TextChipAdapter(mBinding.chipsRoom, getLayoutInflater());
        adapter.setOnChipSelectedListener(index -> {
            mViewModel.saveRoomsCount(index);
            NavHostFragment.findNavController(this).navigate(R.id.action_roomsFragment_to_areaFragment);
        });
        if (mViewModel.isFlat()) {
            bindAsFlat();
        } else {
            bindAsHouse();
        }
    }

    private void initToolbar() {
        mBinding.toolbar.setNavigationOnClickListener(v ->
                NavHostFragment.findNavController(this).navigateUp()
        );
    }

    private void bindAsFlat() {
        adapter.bind(
                com.monke.ui.R.string.studio,
                com.monke.ui.R.plurals.rooms,
                Constants.MAX_ROOMS,
                com.monke.ui.R.layout.item_chip_button);
    }

    private void bindAsHouse() {
        mBinding.txtHdr.setText(com.monke.ui.R.string.rooms_count_house);
        adapter.bind(
                com.monke.ui.R.string.studio,
                com.monke.ui.R.plurals.rooms,
                Constants.MAX_ROOMS_HOUSE,
                com.monke.ui.R.layout.item_chip_button,
                1);
    }


}