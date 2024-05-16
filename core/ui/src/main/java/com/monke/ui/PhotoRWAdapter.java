package com.monke.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.monke.ui.databinding.ItemPhotoBinding;

import java.util.ArrayList;
import java.util.List;

public class PhotoRWAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> photoUris = new ArrayList<>();

    private static int PHOTO_VIEW_TYPE = 0;
    private static int ADD_PHOTO_VIEW_TYPE = 1;

    private AddPhotoButtonViewHolder.OnAddButtonClickedListener addButtonClickedListener;
    private ProfilePictureViewHolder.OnRemovePhotoListener onRemovePhotoListener;
    private int photoWidth, photoHeight;

    public PhotoRWAdapter(int photoWidth, int photoHeight) {
        this.photoWidth = photoWidth;
        this.photoHeight = photoHeight;
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
        ItemPhotoBinding binding = ItemPhotoBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        if (viewType == PHOTO_VIEW_TYPE) {
            return new ProfilePictureViewHolder(binding, photoHeight, photoWidth);
        }
        return new AddPhotoButtonViewHolder(binding, photoHeight, photoWidth);
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
