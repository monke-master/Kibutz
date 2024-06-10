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
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigation.RentalNavigationContract;
import com.monke.rental.databinding.FragmentUserRentalListBinding;
import com.monke.rental.di.RentalComponentProvider;
import com.monke.ui.LinearSpacingItemDecoration;
import com.monke.ui.rental.RentalRWAdapter;

import javax.inject.Inject;

public class RentalUserListFragment extends Fragment {

    private RentalUserListViewModel mViewModel;
    private FragmentUserRentalListBinding mBinding;
    private RentalRWAdapter adapter;

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

        initRentalRW();
        initNewRentalBtn();
        observeUser();
    }

    private void initRentalRW() {
        adapter = new RentalRWAdapter();
        adapter.setShowRespondBtn(false);
        adapter.setOnItemClickListener(rental -> {
            NavHostFragment
                    .findNavController(this)
                    .navigate(RentalNavigationContract.createDeepLinkRequest(rental.getId()));
        });

        RecyclerView recyclerView = mBinding.listRental;
        recyclerView.setLayoutManager(new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false
        ));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new LinearSpacingItemDecoration(
                (int)getResources().getDimension(com.monke.ui.R.dimen.linear_layout_vertical_padding)
        ));
    }

    private void observeUser() {
        mViewModel.user.observe(getViewLifecycleOwner(), user -> {
            adapter.setRentalList(user.getRentals());
        });
    }

    private void initNewRentalBtn() {
        mBinding.btnNew.setOnClickListener(v -> {
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.action_rentalUserListFragment_to_nav_new_rental);
        });
    }


}