package com.monke.ui;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.monke.ui.databinding.ItemFlatmateBinding;
import com.monke.user.User;

public class FlatmateViewHolder extends RecyclerView.ViewHolder {

    private ItemFlatmateBinding mBinding;

    public interface OnItemClickedListener {
        void onItemClicked(User user);
    }

    public FlatmateViewHolder(@NonNull ItemFlatmateBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public void bind(User user, OnItemClickedListener listener) {
        mBinding.getRoot().setOnClickListener(v -> listener.onItemClicked(user));

        Glide
            .with(itemView.getContext())
            .load(user.getProfile().getPhotosUrl().get(0))
            .circleCrop()
            .into(mBinding.image);

        mBinding.txtName.setText(user.getName());
    }
}
