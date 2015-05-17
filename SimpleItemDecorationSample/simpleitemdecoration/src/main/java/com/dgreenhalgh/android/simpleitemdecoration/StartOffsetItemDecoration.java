package com.dgreenhalgh.android.simpleitemdecoration;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class StartOffsetItemDecoration extends RecyclerView.ItemDecoration {

    private int mOffsetPx;

    public StartOffsetItemDecoration(int offsetPx) {
        mOffsetPx = offsetPx;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent.getChildAdapterPosition(view) < 1) {
            int orientation = ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
            if (orientation == LinearLayoutManager.HORIZONTAL) {
                outRect.left = mOffsetPx;
            } else if (orientation == LinearLayoutManager.VERTICAL) {
                outRect.top = mOffsetPx;
            }
        }
    }
}
