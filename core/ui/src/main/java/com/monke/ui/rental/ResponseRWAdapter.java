package com.monke.ui.rental;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.monke.ui.databinding.ItemResponseBinding;
import com.monke.ui.user.UserDiffUtilCallback;
import com.monke.user.User;

import java.util.ArrayList;
import java.util.List;

public class ResponseRWAdapter extends RecyclerView.Adapter<ResponseViewHolder> {

    private List<User> usersList = new ArrayList<>();
    private ResponseViewHolder.ResponseInteractor responseInteractor;

    public void setResponseInteractor(ResponseViewHolder.ResponseInteractor responseInteractor) {
        this.responseInteractor = responseInteractor;
    }

    @NonNull
    @Override
    public ResponseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemResponseBinding binding = ItemResponseBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ResponseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ResponseViewHolder holder, int position) {
        holder.bind(usersList.get(position), responseInteractor);
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
