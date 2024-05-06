package com.monke.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.monke.ui.databinding.ItemProfilePhotoBinding;

import java.util.ArrayList;
import java.util.List;

public class ProfilePictureRWAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> photoUris;

    private static int PHOTO_VIEW_TYPE = 0;
    private static int ADD_PHOTO_VIEW_TYPE = 1;

    private AddPhotoButtonViewHolder.OnAddButtonClickedListener addButtonClickedListener;
    private ProfilePictureViewHolder.OnRemovePhotoListener onRemovePhotoListener;

    public ProfilePictureRWAdapter(List<String> photoUris) {
        this.photoUris = photoUris;
    }

    public void setAddButtonClickedListener(AddPhotoButtonViewHolder.OnAddButtonClickedListener addButtonClickedListener) {
        this.addButtonClickedListener = addButtonClickedListener;
    }

    public void setOnRemovePhotoListener(ProfilePictureViewHolder.OnRemovePhotoListener onRemovePhotoListener) {
        this.onRemovePhotoListener = onRemovePhotoListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProfilePhotoBinding binding = ItemProfilePhotoBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        if (viewType == PHOTO_VIEW_TYPE) {
            return new ProfilePictureViewHolder(binding);
        }
        return new AddPhotoButtonViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == PHOTO_VIEW_TYPE) {
            ((ProfilePictureViewHolder)holder).bind(
                    photoUris.get(position), onRemovePhotoListener, position);
        } else {
            ((AddPhotoButtonViewHolder)holder).bind(addButtonClickedListener);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == photoUris.size()) {
            return ADD_PHOTO_VIEW_TYPE;
        }
        return PHOTO_VIEW_TYPE;
    }

    @Override
    public int getItemCount() {
        return photoUris.size() + 1;
    }

    public void setPhotoUris(List<String> photoUris) {
        DiffUtil.DiffResult diffResult = DiffUtil
                .calculateDiff(new PhotoDiffUtilCallback(this.photoUris, photoUris));
        this.photoUris.clear();
        this.photoUris.addAll(photoUris);
        diffResult.dispatchUpdatesTo(this);
    }
}
