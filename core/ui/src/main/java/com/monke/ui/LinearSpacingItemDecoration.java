package com.monke.ui;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LinearSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spacing;

    public LinearSpacingItemDecoration(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect,
                               @NonNull View view,
                               @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        if (parent.getChildItemId(view) + 1 == parent.getChildCount()) return;
        if (isVertical(parent)) {
            outRect.bottom = spacing;
        } else {
            outRect.right = spacing;
        }
    }

    private boolean isVertical(RecyclerView parent) {
        return ((LinearLayoutManager)parent.getLayoutManager()).getOrientation() == LinearLayoutManager.VERTICAL;
    }
}
