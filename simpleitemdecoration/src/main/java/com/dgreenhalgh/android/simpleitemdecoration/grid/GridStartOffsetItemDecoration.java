package com.dgreenhalgh.android.simpleitemdecoration.grid;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GridStartOffsetItemDecoration extends RecyclerView.ItemDecoration {

    private int mOffsetPx;
    private int mNumColumns;

    public GridStartOffsetItemDecoration(int offsetPx, int numColumns) {
        mOffsetPx = offsetPx;
        mNumColumns = numColumns;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        boolean childIsInTopRow = parent.getChildAdapterPosition(view) < mNumColumns;
        if (childIsInTopRow) {
            outRect.top = mOffsetPx;
        }
    }
}
