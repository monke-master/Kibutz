package com.monke.ui.rental;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.monke.rental.Address;
import com.monke.ui.databinding.ItemSearchResultBinding;

public class SearchResultViewHolder extends RecyclerView.ViewHolder {

    public interface SearchResultInteractor {
        void onItemClicked(Address address);
    }

    private ItemSearchResultBinding mBinding;

    public SearchResultViewHolder(@NonNull ItemSearchResultBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public void bind(Address result, SearchResultInteractor interactor) {
        mBinding.txtName.setText(result.getName());
        mBinding.txtDescription.setText(result.getDescription());
        mBinding.getRoot().setOnClickListener(v -> interactor.onItemClicked(result));
    }



}
