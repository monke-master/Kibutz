package com.monke.ui;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.monke.ui.databinding.ItemProfilePhotoBinding;

public class ProfilePictureViewHolder extends RecyclerView.ViewHolder {

    private ItemProfilePhotoBinding binding;

    public ProfilePictureViewHolder(@NonNull ItemProfilePhotoBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(String uri, OnRemovePhotoListener onRemovePhotoListener, int pos) {
        Glide
             .with(itemView.getContext())
             .load(uri)
             .into(binding.image);
        binding.btnRemove.setOnClickListener(v -> onRemovePhotoListener.OnRemove(pos));
    }

    public interface OnRemovePhotoListener {
        void OnRemove(int pos);
    }
}