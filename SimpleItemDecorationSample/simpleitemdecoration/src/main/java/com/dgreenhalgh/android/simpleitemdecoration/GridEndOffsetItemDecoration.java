package com.dgreenhalgh.android.simpleitemdecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GridEndOffsetItemDecoration extends RecyclerView.ItemDecoration {

    private int mOffsetPx;
    private int mNumColumns;

    public GridEndOffsetItemDecoration(int offsetPx, int numColumns) {
        mOffsetPx = offsetPx;
        mNumColumns = numColumns;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int itemCount = state.getItemCount();
        int numChildrenOnLastRow = itemCount % mNumColumns;
        if (numChildrenOnLastRow == 0) {
            numChildrenOnLastRow = mNumColumns;
        }

        boolean childIsInBottomRow = parent.getChildAdapterPosition(view) >= itemCount - numChildrenOnLastRow;
        if (childIsInBottomRow) {
            outRect.bottom = mOffsetPx;
        }
    }
}
