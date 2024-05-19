package com.monke.ui.photo;

import android.content.Context;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.example.navigation.PhotoNavigationContract;
import com.monke.ui.databinding.FragmentPhotoBinding;

public class PhotoFragment extends Fragment {

    private String photoUri;
    private FragmentPhotoBinding mBinding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        getArgs();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentPhotoBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBinding.toolbar.setNavigationOnClickListener(v ->
                NavHostFragment.findNavController(this).navigateUp()
        );

        Glide
            .with(getContext())
            .load(photoUri)
            .centerCrop()
            .into(mBinding.image);
    }

    private void getArgs() {
        String encodedPhoto = getArguments().getString("photo");
        byte[] photoBytes = Base64.decode(encodedPhoto, Base64.DEFAULT);
        photoUri = new String(photoBytes);
    }
}