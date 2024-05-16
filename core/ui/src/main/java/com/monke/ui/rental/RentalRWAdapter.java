package com.monke.ui.rental;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.monke.rental.Rental;
import com.monke.ui.databinding.ItemRentalBinding;

import java.util.ArrayList;
import java.util.List;

public class RentalRWAdapter extends RecyclerView.Adapter<RentalViewHolder> {

    private List<Rental> rentalList = new ArrayList<>();
    private RentalViewHolder.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(RentalViewHolder.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RentalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var binding = ItemRentalBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RentalViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RentalViewHolder holder, int position) {
        holder.bind(rentalList.get(position), onItemClickListener);
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
