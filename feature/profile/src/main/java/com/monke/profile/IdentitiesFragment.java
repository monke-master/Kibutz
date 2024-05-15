package com.monke.profile;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monke.identity.Identity;
import com.monke.identity.IdentityModel;
import com.monke.profile.databinding.FragmentIdentitiesBinding;
import com.monke.profile.di.ProfileComponentProvider;
import com.monke.ui.IdentityChipAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class IdentitiesFragment extends Fragment {

    private FragmentIdentitiesBinding mBinding;
    private IdentitiesViewModel mViewModel;

    @Inject
    public IdentitiesViewModel.Factory factory;
    public static String RESULT_KEY = "CHIPS_RESULT";
    public static String IDENTITIES_KEY = "IDENTITIES";
    public static String UNAVAILABLE_IDS_KEY = "UNAVAILABLE";
    public static String IDENTITIES_TYPES_KEY = "IDENTITIES_TYPES";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ProfileComponentProvider.getInstance().inject(this);
        mViewModel = new ViewModelProvider(this, factory).get(IdentitiesViewModel.class);
        getArgs();
        mViewModel.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentIdentitiesBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        observeIdentitiesList();
    }

    private void observeIdentitiesList() {
        IdentityChipAdapter adapter = new IdentityChipAdapter(mBinding.chipsIdentities, getLayoutInflater());
        mViewModel.identities.observe(getViewLifecycleOwner(), identities -> {
            adapter.bind(identities, (identity, isChecked) -> {
                mViewModel.onIdentityStatusChanged(identity, isChecked);
            });
        });

        mBinding.toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == com.monke.ui.R.id.btn_pick) {
                setResult();
            }
            return false;
        });
    }


    private void setResult() {
        Bundle bundle = new Bundle();
        List<IdentityModel> identities = mViewModel
                .getSelectedIdentities()
                .stream()
                .map(IdentityModel::new)
                .collect(Collectors.toList());
        bundle.putParcelableArrayList(IDENTITIES_KEY, new ArrayList<>(identities));
        getParentFragmentManager().setFragmentResult(IdentitiesFragment.RESULT_KEY, bundle);
        NavHostFragment
                .findNavController(this)
                .navigateUp();
    }

    private void getArgs() {
        Bundle bundle = getArguments();
        if (bundle == null) return;
        List<Identity> unavailableIds = bundle
                .getParcelableArrayList(UNAVAILABLE_IDS_KEY)
                .stream()
                .map(i -> ((IdentityModel)i).getIdentity())
                .collect(Collectors.toList());
        mViewModel.setUnavailableIdentities(unavailableIds);

        List<Identity.Type> types = bundle
                .getStringArrayList(IDENTITIES_TYPES_KEY)
                .stream()
                .map(Identity.Type::valueOf)
                .collect(Collectors.toList());
        mViewModel.setRequiredTypes(types);
    }
}