package com.monke.rental;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDeepLinkRequest;
import androidx.navigation.fragment.NavHostFragment;

import com.monke.rental.databinding.FragmentUserRentalListBinding;
import com.monke.rental.di.RentalComponentProvider;

import javax.inject.Inject;

public class RentalUserListFragment extends Fragment {

    private RentalUserListViewModel mViewModel;
    private FragmentUserRentalListBinding mBinding;

    @Inject
    public RentalUserListViewModel.Factory factory;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        RentalComponentProvider.getInstance().inject(this);
        mViewModel = new ViewModelProvider(this, factory).get(RentalUserListViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentUserRentalListBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBinding.btnNew.setOnClickListener(v -> {
            NavDeepLinkRequest request = NavDeepLinkRequest.Builder
                    .fromUri(Uri.parse(getString(com.monke.ui.R.string.new_rental_deeplink)))
                    .build();
            NavHostFragment.findNavController(this).navigate(request);
        });
    }
}