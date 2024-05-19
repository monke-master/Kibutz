package com.monke.ui.rental;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.monke.rental.Address;
import com.monke.ui.databinding.ItemSearchResultBinding;

import java.util.ArrayList;
import java.util.List;

public class SearchResultRWAdapter extends RecyclerView.Adapter<SearchResultViewHolder> {

    private List<Address> addresses = new ArrayList<>();

    private SearchResultViewHolder.SearchResultInteractor interactor;

    public void setInteractor(SearchResultViewHolder.SearchResultInteractor interactor) {
        this.interactor = interactor;
    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var binding = ItemSearchResultBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new SearchResultViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position) {
        holder.bind(addresses.get(position), interactor);
    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }

    public void setResponses(List<Address> addresses) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(
                new AddressDiffUtilCallback(this.addresses, addresses));
        this.addresses.clear();
        this.addresses.addAll(addresses);
        diffResult.dispatchUpdatesTo(this);
    }

}
