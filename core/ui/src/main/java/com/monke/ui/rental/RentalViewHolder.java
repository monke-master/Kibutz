package com.monke.ui.rental;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.monke.rental.Flat;
import com.monke.rental.House;
import com.monke.rental.Realty;
import com.monke.rental.Rental;
import com.monke.ui.R;
import com.monke.ui.databinding.ItemRentalBinding;
import com.monke.utils.StringsHelper;

import java.util.Formatter;

public class RentalViewHolder extends RecyclerView.ViewHolder {
    private final ItemRentalBinding mBinding;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(Rental rental);
        default void onRespondBtnClick(Rental rental) {};
    }

    public RentalViewHolder(@NonNull ItemRentalBinding binding) {
        super(binding.getRoot());
        this.mBinding = binding;
    }

    public void bind(Rental rental,
                     boolean showRespondBtn,
                     OnItemClickListener onItemClickListener) {
        context = itemView.getContext();
        mBinding.getRoot().setOnClickListener(v -> onItemClickListener.onItemClick(rental));


        Realty realty = rental.getRealty();
        mBinding.txtPrice.setText(context.getString(R.string.price_info, rental.getPrice()));
        mBinding.txtArea.setText(context.getString(
                                R.string.area_info,
                                StringsHelper.formatFloat(realty.getArea()))
        );

        String flatmatesInfo = context.getResources().getQuantityString(
                R.plurals.flatmates,
                rental.getMaxFlatmatesCount(),
                rental.getMaxFlatmatesCount());
        mBinding.txtFlatmates.setText(flatmatesInfo);

        mBinding.txtAddress.setText(realty.getAddress());

        Glide
            .with(itemView.getContext())
            .load(rental.getPhotos().get(0))
            .centerCrop()
            .into(mBinding.image);

        if (!showRespondBtn) {
            mBinding.btnRespond.setVisibility(View.GONE);
        } else {
            mBinding.btnRespond.setOnClickListener(v -> onItemClickListener.onRespondBtnClick(rental));
        }

        if (realty.isFlat()) {
            bindFlatInfo((Flat) realty);
        } else {
            bindHouseInfo((House) realty);
        }
    }

    private void bindFlatInfo(Flat flat) {
        mBinding.txtFloor.setText(context.getString(R.string.floors_info, flat.getFloor(), flat.getFloorsCount()));
        mBinding.txtRooms.setText(context.getString(R.string.rooms_info, flat.getRoomsCount()));
    }

    private void bindHouseInfo(House house) {
        mBinding.txtFloor.setText(context.getString(R.string.floors_info_house, house.getFloorsCount()));
        mBinding.txtRooms.setText(context.getString(R.string.rooms_info_house, house.getRoomsCount()));
    }
}
