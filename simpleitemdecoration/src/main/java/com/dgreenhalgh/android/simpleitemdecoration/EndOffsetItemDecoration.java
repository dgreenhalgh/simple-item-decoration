package com.dgreenhalgh.android.simpleitemdecoration;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Adds an offset to the end of a RecyclerView using a LinearLayoutManager or
 * its subclass.
 * <p>
 * If the RecyclerView.LayoutManager is oriented vertically, the offset will be
 * added to the bottom of the RecyclerView. If the LayoutManager is oriented
 * horizontally, the offset will be added to the right of the RecyclerView.
 */
public class EndOffsetItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable offsetDrawable;
    private int offsetPx;

    /**
     * Constructor that takes in a {@link Drawable} to be drawn at the end of
     * the RecyclerView.
     *
     * @param offsetDrawable The {@code Drawable} to be added to the end of
     *                       the RecyclerView
     */
    public EndOffsetItemDecoration(Drawable offsetDrawable) { // TODO: Crashes on rotation?
        this.offsetDrawable = offsetDrawable;
    }

    /**
     * Constructor that takes in the size of the offset to be added to the
     * end of the RecyclerView.
     *
     * @param offsetPx The size of the offset to be added to the end of the
     *                 RecyclerView in pixels
     */
    public EndOffsetItemDecoration(int offsetPx) {
        this.offsetPx = offsetPx;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int orientation = ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
        int lastSpanMaxIndex = getLastSpanMaxIndex(parent, state, orientation);

        if (parent.getChildAdapterPosition(view) < lastSpanMaxIndex) {
            return;
        }

        if (orientation == LinearLayoutManager.HORIZONTAL) {
            if (offsetPx > 0) {
                outRect.right = offsetPx;
            } else if (offsetDrawable != null) {
                outRect.right = offsetDrawable.getIntrinsicWidth();
            }
        } else if (orientation == LinearLayoutManager.VERTICAL) {
            if (offsetPx > 0) {
                outRect.bottom = offsetPx;
            } else if (offsetDrawable != null) {
                outRect.bottom = offsetDrawable.getIntrinsicHeight();
            }
        }
    }

    private int getLastSpanMaxIndex(RecyclerView parent, RecyclerView.State state, int orientation) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int itemCount = state.getItemCount();
        int lastSpanMaxIndex = itemCount - 1;

        if (layoutManager instanceof GridLayoutManager) {
            int spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
//            if (orientation == LinearLayoutManager.HORIZONTAL) {
            lastSpanMaxIndex = itemCount - getLastSpanItemCount(itemCount, spanCount);
//            } else if (orientation == LinearLayoutManager.VERTICAL) {
//                lastSpanMaxIndex = itemCount - getLastRowItemCount(itemCount, spanCount);
//            }
        }

        return lastSpanMaxIndex;
    }

//    private int getLastColumnItemCount(int itemCount, int spanCount) {
//
//    }

    private int getLastSpanItemCount(int itemCount, int spanCount) { // TODO: Pull params in
        int lastRowChildCount = itemCount % spanCount;
        if (lastRowChildCount == 0) {
            lastRowChildCount = spanCount;
        }

        return lastRowChildCount;
    }
}
