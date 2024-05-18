package com.monke.ui.rental;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.monke.ui.databinding.ItemResponseBinding;
import com.monke.user.User;

public class ResponseViewHolder extends RecyclerView.ViewHolder {

    public interface ResponseInteractor {
        void onCancel(User user);
        void onLike(User user);
        void onAddFlatmate(User user);
        void onRemoveFlatmate(User user);
    }

    private ItemResponseBinding mBinding;

    public ResponseViewHolder(@NonNull ItemResponseBinding binding) {
        super(binding.getRoot());
        this.mBinding = binding;
    }


    public void bind(User user, ResponseInteractor interactor) {
        Glide
            .with(itemView.getContext())
            .load(user.getProfile().getPhotosUrl().get(0))
            .into(mBinding.image);

        mBinding.btnCancel.setOnClickListener(v -> interactor.onCancel(user));
    }
}
