package com.monke.ui.photo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.monke.ui.R;
import com.monke.ui.databinding.ItemRentalPhotoBinding;

import java.util.List;

public class PhotoPagerAdapter extends PagerAdapter {

    public interface OnPhotoClickedListener {
        void onClicked(String uri);
    }

    private List<String> photoUris;
    private Context context;
    private int layoutId;
    private OnPhotoClickedListener onPhotoClickedListener;


    public PhotoPagerAdapter(List<String> photoUris, Context context, int layoutId) {
        this.photoUris = photoUris;
        this.context = context;
        this.layoutId = layoutId;
    }

    public void setOnPhotoClickedListener(OnPhotoClickedListener onPhotoClickedListener) {
        this.onPhotoClickedListener = onPhotoClickedListener;
    }

    @Override
    public int getCount() {
        return photoUris.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        var view = LayoutInflater.from(context).inflate(layoutId, container, false);

        Glide
            .with(context)
            .load(photoUris.get(position))
            .centerCrop()
            .into((ImageView) view.findViewById(R.id.image));

        view.setOnClickListener(v ->
                onPhotoClickedListener.onClicked(photoUris.get(position)));

        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
