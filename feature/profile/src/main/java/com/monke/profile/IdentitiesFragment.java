package com.monke.profile;

import static com.example.navigation.PickIdentitiesContract.IDENTITIES_KEY;
import static com.example.navigation.PickIdentitiesContract.IDENTITIES_TYPES_KEY;
import static com.example.navigation.PickIdentitiesContract.RESULT_KEY;
import static com.example.navigation.PickIdentitiesContract.UNAVAILABLE_IDS_KEY;

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
import com.monke.ui.chips.IdentityChipAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class IdentitiesFragment extends Fragment {

    private FragmentIdentitiesBinding mBinding;
    private IdentitiesViewModel mViewModel;

    @Inject
    public IdentitiesViewModel.Factory factory;

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
        getParentFragmentManager().setFragmentResult(RESULT_KEY, bundle);
        NavHostFragment
                .findNavController(this)
                .navigateUp();
    }

    private void getArgs() {
        Bundle bundle = getArguments();
        if (bundle == null) return;

        String[] unavailableIds = bundle.getStringArray(UNAVAILABLE_IDS_KEY);
        if (unavailableIds != null) {
            mViewModel.setUnavailableIdentities(List.of(unavailableIds));
        }

        List<Identity.Type> types =
                List.of(bundle.getStringArray(IDENTITIES_TYPES_KEY))
                .stream()
                .map(Identity.Type::valueOf)
                .collect(Collectors.toList());
        mViewModel.setRequiredTypes(types);
    }
}