package com.monke.ui.rental;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.monke.ui.databinding.ItemDetailsBinding;

import java.util.ArrayList;
import java.util.List;

public class RealtyDetailRWAdapter extends RecyclerView.Adapter<RealtyDetailRWAdapter.RealtyDetailViewHolder> {

    private List<RealtyDetail> detailList = new ArrayList<>();

    public void setDetailList(List<RealtyDetail> detailList) {
        this.detailList = detailList;
        notifyDataSetChanged();
    }

    public class RealtyDetailViewHolder extends RecyclerView.ViewHolder {

        private final ItemDetailsBinding mBinding;

        public RealtyDetailViewHolder(@NonNull ItemDetailsBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(RealtyDetail detail) {
            mBinding.txtName.setText(detail.getName());
            mBinding.txtValue.setText(detail.getValue().toString());
        }
    }


    @NonNull
    @Override
    public RealtyDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDetailsBinding binding = ItemDetailsBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new RealtyDetailViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RealtyDetailViewHolder holder, int position) {
        holder.bind(detailList.get(position));
    }

    @Override
    public int getItemCount() {
        return detailList.size();
    }
}
