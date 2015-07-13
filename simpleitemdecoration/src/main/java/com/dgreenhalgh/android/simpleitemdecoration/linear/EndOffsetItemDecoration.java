package com.dgreenhalgh.android.simpleitemdecoration.linear;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * An ItemDecoration used to add an offset to the end of a RecyclerView using a
 * LinearLayoutManager.
 * <p/>
 * If the LinearLayoutManager is oriented vertically, the offset will be added
 * to the bottom of the RecyclerView. If the LinearLayoutManager is oriented
 * horizontally, the offset will be added to the right of the RecyclerView.
 */
public class EndOffsetItemDecoration extends RecyclerView.ItemDecoration {

    private int mOffsetPx;

    /**
     * Constructor that takes in the size of the offset to be added to the end
     * of the RecyclerView
     *
     * @param offsetPx The size of the offset to be added to the end of the
     *                 RecyclerView in pixels
     */
    public EndOffsetItemDecoration(int offsetPx) {
        mOffsetPx = offsetPx;
    }

    /**
     * Override of RecyclerView.ItemDecoration#getItemOffsets
     * <p/>
     * This implementation determines the size and location of the offset to be
     * added to the end of the RecyclerView.
     *
     * @param outRect The Rect of offsets to be added around the child view
     * @param view The child view to be decorated with an offset
     * @param parent The RecyclerView onto which an offset is being added
     * @param state The current state of the RecyclerView
     */
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
