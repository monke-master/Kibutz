package com.monke.ui;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;
import java.util.Objects;

public class PhotoDiffUtilCallback extends DiffUtil.Callback {

    private final List<String> oldList;
    private final List<String> newList;

    public PhotoDiffUtilCallback(List<String> oldList, List<String> newList) {
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
