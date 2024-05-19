package com.monke.ui.rental;

import androidx.recyclerview.widget.DiffUtil;

import com.monke.rental.Address;
import com.monke.rental.Response;

import java.util.List;
import java.util.Objects;

public class AddressDiffUtilCallback extends DiffUtil.Callback {

    private final List<Address> oldList;
    private final List<Address> newList;

    public AddressDiffUtilCallback(List<Address> oldList, List<Address> newList) {
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
        return Objects.equals(oldList.get(oldItemPosition), newList.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return Objects.equals(oldList.get(oldItemPosition), newList.get(newItemPosition));
    }
}
