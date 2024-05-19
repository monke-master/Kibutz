package com.monke.auth.ui.email;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.monke.auth.databinding.DialogConfirmEmailBinding;

public class EmailConfirmationDialog extends DialogFragment {

    @Override
    public void onStart() {
        super.onStart();

        setCancelable(false);
        getDialog().getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        var binding = DialogConfirmEmailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }



}
