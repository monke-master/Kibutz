package com.monke.ui.rental;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.monke.rental.Response;
import com.monke.ui.databinding.ItemResponseBinding;
import com.monke.ui.user.UserDiffUtilCallback;
import com.monke.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ResponseRWAdapter extends RecyclerView.Adapter<ResponseViewHolder> {

    private List<Pair<Response, User>> responses = new ArrayList<>();
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
        holder.bind(responses.get(position), responseInteractor);
    }

    @Override
    public int getItemCount() {
        return responses.size();
    }

    public void setResponses(List<Pair<Response, User>> responses) {
        DiffUtil.DiffResult diffResult = DiffUtil
                .calculateDiff(
                        new ResponseDiffUtilCallback(
                                this.responses.stream().map(p -> p.first).collect(Collectors.toList()),
                                responses.stream().map(p -> p.first).collect(Collectors.toList())
                        )
                );
        this.responses.clear();
        this.responses.addAll(responses);
        diffResult.dispatchUpdatesTo(this);
    }

}
