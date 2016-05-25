package com.dgreenhalgh.android.simpleitemdecoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable divider;

    private int offsetPx;

    private Drawable horizontalDivider;
    private Drawable verticalDivider;

    private int horizontalOffsetPx;
    private int verticalOffsetPx;

    /**
     * Constructor that takes in a {@link Drawable} to be used as the interior divider in a
     * RecyclerView with a LinearLayoutManager.
     *
     * @param divider A divider {@code Drawable} to be drawn onto the RecyclerView
     */
    public DividerItemDecoration(Drawable divider) {
        this.divider = divider;
    }

    /**
     * Constructor that takes in the size of an offset (in pixels) to be used as the interior
     * divider in a RecyclerView with a LinearLayoutManager.
     *
     * @param offsetPx The size of the offset in pixels to be added between items in the
     *                 RecyclerView
     *
     */
    public DividerItemDecoration(int offsetPx) {
        this.offsetPx = offsetPx;
    }

    /**
     * Constructor that takes in two {@link Drawable} objects to be used as horizontal and vertical
     * dividers in a RecyclerView with a GridLayoutManager.
     *
     * @param horizontalDivider A divider {@code Drawable} to be drawn on the rows of the grid of
     *                          the RecyclerView
     * @param verticalDivider A divider {@code Drawable} to be drawn on the columns of the grid of
     *                        the RecyclerView.
     */
    public DividerItemDecoration(Drawable horizontalDivider, Drawable verticalDivider) {
        this.horizontalDivider = horizontalDivider;
        this.verticalDivider = verticalDivider;
    }

    /**
     * Constructor that takes in the sizes of offsets (in pixels) to be used as horizontal and
     * vertical dividers in a RecyclerView with a GridLayoutManager.
     *
     * @param horizontalOffsetPx The size of the offset in pixels to be added between rows in the
     *                           RecyclerView
     * @param verticalOffsetPx The size of the offset in pixels to be added between the columns in
     *                         the RecyclerView
     */
    public DividerItemDecoration(int horizontalOffsetPx, int verticalOffsetPx) {
        this.horizontalOffsetPx = horizontalOffsetPx;
        this.verticalOffsetPx = verticalOffsetPx;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            ((GridLayoutManager) layoutManager).getOrientation(); // todo
        } else if (layoutManager instanceof LinearLayoutManager) {
            int orientation = ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
            if (orientation == LinearLayoutManager.HORIZONTAL) {
                drawHorizontalDividersForLinearLayoutManager(c, parent);
            } else if (orientation == LinearLayoutManager.VERTICAL) {
                drawVerticalDividersForLinearLayoutManager(c, parent);
            }
        } else {
            throw new UnsupportedOperationException("Simple ItemDecoration only supports " +
                    "LinearLayoutManager and its children.");
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);


    }

    // todo check offsets

    private void drawHorizontalDividersForLinearLayoutManager(Canvas canvas, RecyclerView parent) {
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int left = child.getRight() + params.rightMargin;
            int right = left + divider.getIntrinsicHeight();

            divider.setBounds(left, top, right, bottom);
            divider.draw(canvas);
        }
    }

    private void drawVerticalDividersForLinearLayoutManager(Canvas canvas, RecyclerView parent) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + divider.getIntrinsicHeight();

            divider.setBounds(left, top, right, bottom);
            divider.draw(canvas);
        }
    }


}
