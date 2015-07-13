package com.dgreenhalgh.android.simpleitemdecoration.grid;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Adds an offset to the bottom of a {@link RecyclerView} with a
 * {@link android.support.v7.widget.GridLayoutManager} or its subclass.
 */
public class GridBottomOffsetItemDecoration extends RecyclerView.ItemDecoration {

    private int mOffsetPx;
    private int mNumColumns;

    /**
     * Sole constructor. Takes in the size of the offset to be added to the
     * bottom of the {@link RecyclerView}.
     *
     * @param offsetPx The size of the offset to be added to the bottom of the
     *                 {@code RecyclerView} in pixels
     * @param numColumns The number of columns in the grid of the {@code RecyclerView}
     */
    public GridBottomOffsetItemDecoration(int offsetPx, int numColumns) {
        mOffsetPx = offsetPx;
        mNumColumns = numColumns;
    }

    /**
     * Determines the size and the location of the offset to be added to the
     * bottom of the {@link RecyclerView}.
     *
     * @param outRect The {@link Rect} of offsets to be added around the child view
     * @param view The child view to be decorated with an offset
     * @param parent The {@code RecyclerView} onto which dividers are being added
     * @param state The current {@link android.support.v7.widget.RecyclerView.State}
     *              of the {@code RecyclerView}
     */
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
