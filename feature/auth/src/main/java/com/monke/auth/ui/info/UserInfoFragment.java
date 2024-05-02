package com.monke.auth.ui.info;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
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
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;

import com.monke.auth.R;
import com.monke.auth.databinding.FragmentUserInfoBinding;
import com.monke.auth.di.AuthComponentProvider;
import com.monke.data.UiStatusState;
import com.monke.ui.DatePickerFragment;
import com.monke.ui.TextChangedListener;
import com.monke.utils.DateHelper;

import java.util.Calendar;

import javax.inject.Inject;

public class UserInfoFragment extends Fragment {

    private UserInfoViewModel mViewModel;
    private FragmentUserInfoBinding binding;

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
        binding = FragmentUserInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initNameEditText();
        initGenderEditText();
        initDateOfBirthEditText();
        observeUiState();
        initNextButton();
        observeUiState();
        observeUiStatus();
    }

    private void initNameEditText() {
        binding.editTxtName.setText(mViewModel.getUiState().getValue().getName());
        binding.editTxtName.addTextChangedListener(new TextChangedListener() {
            @Override
            public void onTextChanged(Editable s) {
                mViewModel.setName(s.toString());
            }
        });
    }

    private void initGenderEditText() {
        var editText = (AutoCompleteTextView)binding.editTxtGender.getEditText();
        var items = getResources().getStringArray(com.monke.ui.R.array.genders);
        var adapter = new ArrayAdapter<String>(
                requireContext(),
                com.monke.ui.R.layout.item_dropdown,
                items);
        editText.setAdapter(adapter);
        editText.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mViewModel.setGender(i == 0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initDateOfBirthEditText() {
        binding.editTxtDateOfBirth.getEditText().setInputType(InputType.TYPE_NULL);
        binding.editTxtDateOfBirth.getEditText().setOnClickListener((view) -> {
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
                binding.editTxtDateOfBirth.getEditText().setText(DateHelper.formatDate(dateOfBirth));
            }
        });
    }

    private void initNextButton() {
        binding.btnNext.setOnClickListener(v -> mViewModel.saveData());
    }

    private void observeUiStatus() {
        mViewModel.getUiStatusState().observe(getViewLifecycleOwner(), uiStatusState -> {
            if (uiStatusState.isSuccess()) {
                NavDeepLinkRequest request = NavDeepLinkRequest.Builder
                        .fromUri(Uri.parse(getString(com.monke.ui.R.string.main_fragment_deeplink)))
                        .build();
                NavHostFragment.findNavController(this).navigate(request);
            }
        });
    }

}