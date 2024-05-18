package com.monke.ui.rental;

import android.graphics.drawable.Drawable;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.monke.rental.Response;
import com.monke.ui.R;
import com.monke.ui.databinding.ItemResponseBinding;
import com.monke.user.User;

public class ResponseViewHolder extends RecyclerView.ViewHolder {

    public interface ResponseInteractor {
        void onDislike(Response response);
        void onLike(Response response);
        void onAddFlatmate(Response response);
        void onRemoveFlatmate(Response response);
    }

    private ItemResponseBinding mBinding;
    private ResponseInteractor interactor;
    private Response response;

    public ResponseViewHolder(@NonNull ItemResponseBinding binding) {
        super(binding.getRoot());
        this.mBinding = binding;
    }


    public void bind(Pair<Response, User> response, ResponseInteractor interactor) {
        this.interactor = interactor;
        this.response = response.first;
        Glide
            .with(itemView.getContext())
            .load(response.second.getProfile().getPhotosUrl().get(0))
            .into(mBinding.image);

        mBinding.btnCancel.setOnClickListener(v -> interactor.onDislike(response.first));

        switch (response.first.getStatus()) {
            case HANGING -> initAsLikeBtn();
            case LIKED -> initAsAddFlatmateBtn();
            case FLATMATE -> initAsRemoveFlatmateBtn();
        }
    }

    private void initAsLikeBtn() {
        mBinding.btnAction.setImageDrawable(getDrawable(R.drawable.ic_like));
        mBinding.btnAction.setOnClickListener(v -> interactor.onLike(response));
    }

    private void initAsAddFlatmateBtn() {
        mBinding.btnAction.setImageDrawable(getDrawable(R.drawable.ic_add_flatmate));
        mBinding.btnAction.setOnClickListener(v -> interactor.onAddFlatmate(response));
    }

    private void initAsRemoveFlatmateBtn() {
        mBinding.btnAction.setImageDrawable(getDrawable(R.drawable.ic_remove_flatmate));
        mBinding.btnAction.setOnClickListener(v -> interactor.onRemoveFlatmate(response));
    }

    private Drawable getDrawable(int id) {
        return AppCompatResources.getDrawable(itemView.getContext(), id);
    }
}
