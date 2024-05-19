package com.monke.ui.rental;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.monke.ui.databinding.LayoutAddressBottomSheetBinding;

public class AddressBottomSheetDialog extends BottomSheetDialogFragment {

    public interface AddressDialogInteractor {
        void onNextButtonClicked();
        void onAddressEditTextClicked();
    }

    public static final String TAG = "AddressBottomSheetDialog";

    private LayoutAddressBottomSheetBinding mBinding;
    private String address;
    private final String ADDRESS_KEY = "address";


    public static AddressBottomSheetDialog newInstance(String address) {
        AddressBottomSheetDialog dialog = new AddressBottomSheetDialog();

        Bundle args = new Bundle();
        args.putString(dialog.ADDRESS_KEY, address);
        dialog.setArguments(args);

        return dialog;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        address = getArguments().getString(ADDRESS_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = LayoutAddressBottomSheetBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        var dialog = (BottomSheetDialog)super.onCreateDialog(savedInstanceState);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return dialog;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        var interactor = (AddressDialogInteractor)getParentFragment();

        mBinding.btnNext.setOnClickListener(v -> interactor.onNextButtonClicked());
        mBinding.editTxtDescription.getEditText().setInputType(InputType.TYPE_NULL);
        mBinding.editTxtDescription.getEditText().setOnClickListener(v -> interactor.onAddressEditTextClicked());

        mBinding.editTxtDescription.getEditText().setText(address);
    }
}
