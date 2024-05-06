package com.monke.ui;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.monke.ui.databinding.ItemProfilePhotoBinding;

public class AddPhotoButtonViewHolder extends RecyclerView.ViewHolder {

    private ItemProfilePhotoBinding mBinding;

    public AddPhotoButtonViewHolder(@NonNull ItemProfilePhotoBinding binding) {
        super(binding.getRoot());
        this.mBinding = binding;
    }

    public void bind(OnAddButtonClickedListener listener) {
        mBinding.image.setImageDrawable(
                AppCompatResources.getDrawable(itemView.getContext(), R.drawable.ic_pick_photo));
        mBinding.btnRemove.setVisibility(View.GONE);
        mBinding.image.setOnClickListener(v -> listener.onClicked());
    }

    public interface OnAddButtonClickedListener {
        void onClicked();
    }
}
