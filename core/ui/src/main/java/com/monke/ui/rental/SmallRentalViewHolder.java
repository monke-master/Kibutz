package com.monke.ui.rental;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.monke.rental.Flat;
import com.monke.rental.Realty;
import com.monke.rental.Rental;
import com.monke.ui.R;
import com.monke.ui.databinding.ItemRentalSmallBinding;

public class SmallRentalViewHolder extends RecyclerView.ViewHolder {
    private ItemRentalSmallBinding mBinding;
    private Context context;

    public SmallRentalViewHolder(@NonNull ItemRentalSmallBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public void bind(Rental rental, RentalViewHolder.OnItemClickListener onItemClickListener) {
        context = itemView.getContext();
        mBinding.getRoot().setOnClickListener(v -> onItemClickListener.onItemClick(rental));


        Realty realty = rental.getRealty();
        mBinding.txtPrice.setText(context.getString(R.string.price_info, rental.getPrice()));
        mBinding.txtRooms.setText(context.getString(R.string.rooms_info, realty.getRoomsCount()));
        mBinding.txtArea.setText(context.getString(R.string.area_info, realty.getArea()));

        String flatmatesInfo = context.getResources().getQuantityString(
                R.plurals.flatmates,
                rental.getMaxFlatmatesCount(),
                rental.getMaxFlatmatesCount());
        mBinding.txtFlatmates.setText(flatmatesInfo);

        Glide
                .with(itemView.getContext())
                .load(rental.getPhotos().get(0))
                .centerCrop()
                .into(mBinding.image);

    }

}
