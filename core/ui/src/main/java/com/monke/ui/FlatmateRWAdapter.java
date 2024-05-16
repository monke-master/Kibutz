package com.monke.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.monke.user.User;

import java.util.ArrayList;
import java.util.List;

import com.monke.ui.databinding.ItemFlatmateBinding;

public class FlatmateRWAdapter extends RecyclerView.Adapter<FlatmateViewHolder> {

    private List<User> usersList = new ArrayList<>();
    private FlatmateViewHolder.OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(FlatmateViewHolder.OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    @NonNull
    @Override
    public FlatmateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFlatmateBinding binding = ItemFlatmateBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new FlatmateViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FlatmateViewHolder holder, int position) {
        holder.bind(usersList.get(position), onItemClickedListener);
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public void setUsersList(List<User> usersList) {
        DiffUtil.DiffResult diffResult = DiffUtil
                .calculateDiff(new UserDiffUtilCallback(this.usersList, usersList));
        this.usersList.clear();
        this.usersList.addAll(usersList);
        diffResult.dispatchUpdatesTo(this);
    }
}
