package com.dgreenhalgh.android.simpleitemdecoration.linear;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Adds an offset to the start of a {@link RecyclerView} using a
 * {@link LinearLayoutManager} or its subclass.
 * <p>
 * If the {@link android.support.v7.widget.RecyclerView.LayoutManager} is
 * oriented vertically, the offset will be added to the top of the
 * {@code RecyclerView}. If the {@code LayoutManager} is oriented horizontally,
 * the offset will be added to the left of the {@code RecyclerView}.
 */
public class StartOffsetItemDecoration extends RecyclerView.ItemDecoration {

    private int mOffsetPx;

    /**
     * Sole constructor. Takes in the size of the offset to be added to the
     * start of the {@link RecyclerView}.
     *
     * @param offsetPx The size of the offset to be added to the start of the
     *                 {@code RecyclerView} in pixels
     */
    public StartOffsetItemDecoration(int offsetPx) {
        mOffsetPx = offsetPx;
    }

    /**
     * Determines the size and location of the offset to be added to the start
     * of the {@link RecyclerView}.
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
