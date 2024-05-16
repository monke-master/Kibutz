package com.monke.ui;

import androidx.recyclerview.widget.DiffUtil;

import com.monke.rental.Rental;

import java.util.List;

public class RentalDiffUtilCallback extends DiffUtil.Callback {

    private final List<Rental> oldList;
    private final List<Rental> newList;

    public RentalDiffUtilCallback(List<Rental> oldList, List<Rental> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId().equals(newList.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
