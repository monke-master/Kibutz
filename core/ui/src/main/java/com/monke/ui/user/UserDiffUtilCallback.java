package com.monke.ui.user;

import androidx.recyclerview.widget.DiffUtil;

import com.monke.user.User;

import java.util.List;
import java.util.Objects;

public class UserDiffUtilCallback extends DiffUtil.Callback {

    private final List<User> oldList;
    private final List<User> newList;

    public UserDiffUtilCallback(List<User> oldList, List<User> newList) {
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
