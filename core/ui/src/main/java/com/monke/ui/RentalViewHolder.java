package com.monke.ui;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.monke.rental.Flat;
import com.monke.rental.Realty;
import com.monke.rental.Rental;
import com.monke.ui.databinding.ItemRentalBinding;

public class RentalViewHolder extends RecyclerView.ViewHolder {
    private final ItemRentalBinding mBinding;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(Rental rental);
    }

    public RentalViewHolder(@NonNull ItemRentalBinding binding) {
        super(binding.getRoot());
        this.mBinding = binding;
    }

    public void bind(Rental rental, OnItemClickListener onItemClickListener) {
        context = itemView.getContext();
        mBinding.getRoot().setOnClickListener(v -> onItemClickListener.onItemClick(rental));


        Realty realty = rental.getRealty();
        mBinding.txtPrice.setText(context.getString(R.string.price_info, rental.getPrice()));
        mBinding.txtRooms.setText(context.getString(R.string.rooms_flat, realty.getRoomsCount()));
        mBinding.txtArea.setText(context.getString(R.string.area_info, realty.getArea()));

        String flatmatesInfo = context.getResources().getQuantityString(
                R.plurals.flatmates,
                rental.getFlatmatesCount(),
                rental.getFlatmatesCount());
        mBinding.txtFlatmates.setText(flatmatesInfo);

        mBinding.txtAddress.setText(realty.getAddress());

        Glide
            .with(itemView.getContext())
            .load(rental.getPhotos().get(0))
            .centerCrop()
            .into(mBinding.image);

        bindFlatInfo((Flat) realty);
    }

    private void bindFlatInfo(Flat flat) {
        mBinding.txtFloor.setText(context.getString(R.string.floors_info, flat.getFloor(), flat.getFloorsCount()));

    }
}
