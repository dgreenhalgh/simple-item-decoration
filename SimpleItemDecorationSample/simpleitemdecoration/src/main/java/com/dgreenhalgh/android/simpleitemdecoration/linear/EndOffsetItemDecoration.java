package com.dgreenhalgh.android.simpleitemdecoration.linear;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class EndOffsetItemDecoration extends RecyclerView.ItemDecoration {

    private int mOffsetPx;

    public EndOffsetItemDecoration(int offsetPx) {
        mOffsetPx = offsetPx;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int itemCount = state.getItemCount();
        if (parent.getChildAdapterPosition(view) == itemCount - 1) {
            int orientation = ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
            if (orientation == LinearLayoutManager.HORIZONTAL) {
                outRect.right = mOffsetPx;
            } else {
                outRect.bottom = mOffsetPx;
            }
        }
    }
}
