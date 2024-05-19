package com.monke.ui.rental;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.monke.rental.Rental;
import com.monke.ui.databinding.ItemRentalBinding;
import com.monke.ui.databinding.ItemRentalSmallBinding;

import java.util.ArrayList;
import java.util.List;

public class RentalRWAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public enum Type {
        DEFAULT, SMALL
    }

    private final int DEFAULT = 0;
    private final int SMALL = 1;

    private List<Rental> rentalList = new ArrayList<>();
    private RentalViewHolder.OnItemClickListener onItemClickListener;
    private boolean showRespondBtn = true;
    private Type type = Type.DEFAULT;

    public void setOnItemClickListener(RentalViewHolder.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setShowRespondBtn(boolean showRespondBtn) {
        this.showRespondBtn = showRespondBtn;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == DEFAULT) {
            var binding = ItemRentalBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new RentalViewHolder(binding);
        }
        var binding = ItemRentalSmallBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SmallRentalViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == DEFAULT) {
            ((RentalViewHolder)holder).bind(rentalList.get(position), showRespondBtn, onItemClickListener);
        }
        ((SmallRentalViewHolder)holder).bind(rentalList.get(position), onItemClickListener);
    }

    @Override
    public int getItemViewType(int position) {
        switch (type) {
            case DEFAULT -> {
                return DEFAULT;
            }
            case SMALL -> {
                return SMALL;
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return rentalList.size();
    }

    public void setRentalList(List<Rental> rentalList) {
        DiffUtil.DiffResult diffResult = DiffUtil
                .calculateDiff(new RentalDiffUtilCallback(this.rentalList, rentalList));
        this.rentalList.clear();
        this.rentalList.addAll(rentalList);
        diffResult.dispatchUpdatesTo(this);
    }
}
