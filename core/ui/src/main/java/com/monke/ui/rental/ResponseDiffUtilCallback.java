package com.monke.ui.rental;

import androidx.recyclerview.widget.DiffUtil;

import com.monke.rental.Response;
import com.monke.user.User;

import java.util.List;
import java.util.Objects;

public class ResponseDiffUtilCallback extends DiffUtil.Callback {

    private final List<Response> oldList;
    private final List<Response> newList;

    public ResponseDiffUtilCallback(List<Response> oldList, List<Response> newList) {
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
