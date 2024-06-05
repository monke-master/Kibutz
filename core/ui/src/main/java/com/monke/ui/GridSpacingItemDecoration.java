package com.monke.ui;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int verticalSpacing;
    private int itemWidth;

    public GridSpacingItemDecoration(int verticalSpacing, int spanCount, int itemWidth) {
        this.spanCount = spanCount;
        this.verticalSpacing = verticalSpacing;
        this.itemWidth = itemWidth;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int column = position % spanCount;
        int horizontalSpacing = parent.getWidth() - spanCount*itemWidth;

        outRect.left = column * horizontalSpacing / spanCount;
        outRect.right = horizontalSpacing - (column + 1) * horizontalSpacing / spanCount;
        if (position >= spanCount) {
            outRect.top = verticalSpacing;
        }
    }

}