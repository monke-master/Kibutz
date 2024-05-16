package com.monke.rental.newrental;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDeepLinkRequest;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.navigation.PickIdentitiesContract;
import com.monke.identity.Identity;
import com.monke.identity.IdentityModel;
import com.monke.rental.R;
import com.monke.rental.databinding.FragmentFlatmatesBinding;
import com.monke.rental.di.RentalComponentProvider;
import com.monke.ui.IdentityChipAdapter;
import com.monke.ui.TextChangedListener;
import com.monke.utils.StringsHelper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class FlatmatesFragment extends Fragment {

    private IdentityChipAdapter mChipAdapter;
    private FlatmatesViewModel mViewModel;
    private FragmentFlatmatesBinding mBinding;

    @Inject
    public FlatmatesViewModel.Factory factory;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        RentalComponentProvider.getInstance().inject(this);
        mViewModel = new ViewModelProvider(this, factory).get(FlatmatesViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentFlatmatesBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mChipAdapter = new IdentityChipAdapter(mBinding.chipsReqs, getLayoutInflater());

        initEditTextFlatmatesCount();
        initAddIdentityChip();
        setFragmentResultListener();
        observeIdentities();
        initNextBtn();
    }

    private void initEditTextFlatmatesCount() {
        mBinding.editTxtCount.getEditText().setText(
                StringsHelper.getIntOrEmpty(mViewModel.getUiState().getFlatmatesCount()));
        mBinding.editTxtCount.getEditText().addTextChangedListener(new TextChangedListener() {
            @Override
            public void onTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    mViewModel.getUiState().setFlatmatesCount(Integer.parseInt(s.toString()));
                }
            }
        });
    }

    private void initAddIdentityChip() {
        mBinding.chipAdd.setOnClickListener(v -> {
            NavDeepLinkRequest request = PickIdentitiesContract.createDeepLinkRequest(
                    mViewModel.getFiltersIds(),
                    List.of(Identity.Type.NEGATIVE.name(), Identity.Type.GENDER.name())
            );

            NavHostFragment.findNavController(this).navigate(request);
        });
    }

    private void setFragmentResultListener() {
        getParentFragmentManager().setFragmentResultListener(PickIdentitiesContract.RESULT_KEY,
                getViewLifecycleOwner(), (requestKey, result) -> {
                    List<Identity> identities =
                            result.getParcelableArrayList(PickIdentitiesContract.IDENTITIES_KEY)
                                    .stream()
                                    .map(i -> ((IdentityModel)i).getIdentity())
                                    .collect(Collectors.toList());
                    mViewModel.addIdentityFilters(identities);
                });
    }

    private void observeIdentities() {
        mViewModel.filters.observe(getViewLifecycleOwner(), filters -> {
            mChipAdapter.bindFromLast(filters, false);
        });
    }

    private void initNextBtn() {
        mBinding.btnNext.setOnClickListener(v -> {
            mViewModel.saveData();
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.action_flatmatesFragment_to_photosFragment);
        });
    }
}