package com.monke.rental.newrental;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.navigation.SearchAddressNavigationContract;
import com.monke.rental.R;
import com.monke.rental.databinding.FragmentSearchAddressBinding;
import com.monke.rental.di.RentalComponentProvider;
import com.monke.ui.TextChangedListener;
import com.monke.ui.rental.SearchResultRWAdapter;

import javax.inject.Inject;

public class SearchAddressFragment extends Fragment {

    @Inject
    public SearchAddressViewModel.Factory factory;

    private SearchAddressViewModel mViewModel;
    private FragmentSearchAddressBinding mBinding;
    private SearchResultRWAdapter mAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        RentalComponentProvider.getInstance().inject(this);
        mViewModel = new ViewModelProvider(this, factory).get(SearchAddressViewModel.class);
        getArgs();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentSearchAddressBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initToolbar();
        initRecyclerView();
        initEditText();
        observeResult();
        initMapBtn();
    }

    private void initToolbar() {
        mBinding.toolbar.setNavigationOnClickListener(v ->
                NavHostFragment.findNavController(this).navigateUp());
    }

    private void initRecyclerView() {
        var recyclerView = mBinding.listAddresses;
        mAdapter = new SearchResultRWAdapter();
        mAdapter.setInteractor(address -> {
            mViewModel.saveResult(address);
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.action_searchAddressFragment_to_roomsFragment);
        });

        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);
    }

    private void observeResult() {
        mViewModel.result.observe(getViewLifecycleOwner(), addresses -> {
            mAdapter.setResponses(addresses);
        });
    }

    private void initEditText() {
        mBinding.editTxtQuery.getEditText().setText(mViewModel.getQuery());
        mBinding.editTxtQuery.getEditText().addTextChangedListener(new TextChangedListener() {
            @Override
            public void onTextChanged(Editable s) {
                mViewModel.searchAddress(s.toString());
            }
        });
    }

    private void initMapBtn() {
        mBinding.btnMap.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigateUp();
        });
    }

    private void getArgs() {
        Bundle bundle = getArguments();
        if (bundle == null) return;
        String address = getArguments().getString(SearchAddressNavigationContract.ADDRESS_KEY);
        mViewModel.searchAddress(address);
    }

}