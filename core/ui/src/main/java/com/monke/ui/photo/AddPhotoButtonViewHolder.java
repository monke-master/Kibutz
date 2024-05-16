package com.monke.ui.photo;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.monke.ui.R;
import com.monke.ui.databinding.ItemPhotoBinding;

public class AddPhotoButtonViewHolder extends RecyclerView.ViewHolder {

    private final int WIDE_WIDTH = 200;

    private ItemPhotoBinding mBinding;
    private int photoWidth, photoHeight;

    public AddPhotoButtonViewHolder(@NonNull ItemPhotoBinding binding, int photoHeight, int photoWidth) {
        super(binding.getRoot());
        this.mBinding = binding;
        this.photoHeight = photoHeight;
        this.photoWidth = photoWidth;
    }

    public void bind(OnAddButtonClickedListener listener) {
        mBinding.image.getLayoutParams().height = photoHeight;
        mBinding.image.getLayoutParams().width = photoWidth;
        if (photoWidth >= itemView.getContext().getResources().getDimension(R.dimen.creating_rental_photo_size)) {
            mBinding.image.setImageDrawable(
                    AppCompatResources.getDrawable(itemView.getContext(), R.drawable.ic_pick_photo_wide));
        } else {
            mBinding.image.setImageDrawable(
                    AppCompatResources.getDrawable(itemView.getContext(), R.drawable.ic_pick_photo));
        }
        mBinding.btnRemove.setVisibility(View.GONE);
        mBinding.image.setOnClickListener(v -> listener.onClicked());
    }

    public interface OnAddButtonClickedListener {
        void onClicked();
    }
}
