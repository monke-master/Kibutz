package com.monke.ui.photo;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.monke.ui.databinding.ItemPhotoBinding;

public class ProfilePictureViewHolder extends RecyclerView.ViewHolder {

    private ItemPhotoBinding mBinding;
    private int photoWidth, photoHeight;

    public ProfilePictureViewHolder(@NonNull ItemPhotoBinding binding, int photoHeight, int photoWidth) {
        super(binding.getRoot());
        this.mBinding = binding;
        this.photoHeight = photoHeight;
        this.photoWidth = photoWidth;
    }

    public void bind(String uri, OnRemovePhotoListener onRemovePhotoListener, int pos) {
        mBinding.image.getLayoutParams().height = photoHeight;
        mBinding.image.getLayoutParams().width = photoWidth;
        Glide
             .with(itemView.getContext())
             .load(uri)
             .centerCrop()
             .into(mBinding.image);
        mBinding.btnRemove.setOnClickListener(v -> onRemovePhotoListener.OnRemove(pos));
    }

    public interface OnRemovePhotoListener {
        void OnRemove(int index);
    }
}