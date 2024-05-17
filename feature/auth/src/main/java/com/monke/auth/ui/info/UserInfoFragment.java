package com.monke.auth.ui.info;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDeepLinkRequest;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.monke.auth.databinding.FragmentUserInfoBinding;
import com.monke.auth.di.AuthComponentProvider;
import com.monke.ui.DatePickerFragment;
import com.monke.ui.TextChangedListener;
import com.monke.utils.DateHelper;

import javax.inject.Inject;

public class UserInfoFragment extends Fragment {

    private UserInfoViewModel mViewModel;
    private FragmentUserInfoBinding mBinding;

    @Inject
    public UserInfoViewModel.Factory factory;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        AuthComponentProvider.component.inject(this);
        mViewModel = new ViewModelProvider(this, factory).get(UserInfoViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentUserInfoBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initToolbar();
        initNameEditText();
        initGenderEditText();
        initDateOfBirthEditText();
        observeUiState();
        initNextButton();
        observeUiState();
        observeUiStatus();
    }

    private void initToolbar() {
        mBinding.toolbar.setNavigationOnClickListener(v ->
                NavHostFragment.findNavController(this).navigateUp());
    }

    private void initNameEditText() {
        mBinding.editTxtName.setText(mViewModel.getUiState().getValue().getName());
        mBinding.editTxtName.addTextChangedListener(new TextChangedListener() {
            @Override
            public void onTextChanged(Editable s) {
                mViewModel.setName(s.toString());
            }
        });
    }

    private void initGenderEditText() {
        var editText = (AutoCompleteTextView) mBinding.editTxtGender.getEditText();
        var items = getResources().getStringArray(com.monke.ui.R.array.genders);
        var adapter = new ArrayAdapter<String>(
                requireContext(),
                com.monke.ui.R.layout.item_dropdown,
                items);
        editText.setAdapter(adapter);
        editText.setOnItemClickListener((parent, view, position, id) ->
                mViewModel.setGender(position == 0)
        );
    }

    private void initDateOfBirthEditText() {
        mBinding.editTxtDateOfBirth.getEditText().setInputType(InputType.TYPE_NULL);
        mBinding.editTxtDateOfBirth.getEditText().setOnClickListener((view) -> {
            var dialog = DatePickerFragment.newInstance((datePicker, year, month, day) -> {
                mViewModel.setDateOfBirth(DateHelper.getLongFromYMD(year, month, day));
            });
            dialog.show(getChildFragmentManager(), dialog.getTag());
        });

    }

    private void observeUiState() {
        mViewModel.getUiState().observe(getViewLifecycleOwner(), userInfoUiState -> {
            var dateOfBirth = userInfoUiState.getDateOfBirth();
            if (dateOfBirth != null) {
                mBinding.editTxtDateOfBirth.getEditText().setText(DateHelper.formatDate(dateOfBirth));
            }
        });
    }

    private void initNextButton() {
        mBinding.btnNext.setOnClickListener(v -> mViewModel.saveData());
    }

    private void observeUiStatus() {
        mViewModel.getUiStatusState().observe(getViewLifecycleOwner(), uiStatusState -> {
            if (uiStatusState.isSuccess()) {
                NavDeepLinkRequest request = NavDeepLinkRequest.Builder
                        .fromUri(Uri.parse(getString(com.monke.ui.R.string.main_fragment_deeplink)))
                        .build();
                NavHostFragment.findNavController(this).navigate(request);
                AuthComponentProvider.clear();
            }
        });
    }

}