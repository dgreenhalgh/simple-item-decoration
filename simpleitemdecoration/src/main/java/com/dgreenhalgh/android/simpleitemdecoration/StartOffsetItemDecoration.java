package com.dgreenhalgh.android.simpleitemdecoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Adds an offset to the start of a RecyclerView using a LinearLayoutManager or
 * its subclass.
 * <p>
 * If the RecyclerView.LayoutManager is oriented vertically, the offset will be
 * added to the top of the RecyclerView. If the LayoutManager is oriented
 * horizontally, the offset will be added to the left of the RecyclerView.
 */
public class StartOffsetItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable offsetDrawable;
    private int offsetPx;

    /**
     * Constructor that takes in a {@link Drawable} to be drawn at the start of
     * the RecyclerView.
     *
     * @param offsetDrawable The {@code Drawable} to be added to the start of
     *                       the RecyclerView
     */
    public StartOffsetItemDecoration(Drawable offsetDrawable) {
        this.offsetDrawable = offsetDrawable;
    }

    /**
     * Constructor that takes in the size of the offset to be added to the
     * start of the RecyclerView.
     *
     * @param offsetPx The size of the offset to be added to the start of the
     *                 RecyclerView in pixels
     */
    public StartOffsetItemDecoration(int offsetPx) {
        this.offsetPx = offsetPx;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int firstRowMaxIndex = 0;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            firstRowMaxIndex = ((GridLayoutManager) layoutManager).getSpanCount() - 1;
        }

        if (parent.getChildAdapterPosition(view) > firstRowMaxIndex) {
            return;
        }

        int orientation = ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            if (offsetDrawable != null) {
                outRect.left = offsetDrawable.getIntrinsicWidth();
            } else if (offsetPx > 0) {
                outRect.left = offsetPx;
            }
        } else if (orientation == LinearLayoutManager.VERTICAL) {
            if (offsetDrawable != null) {
                outRect.top = offsetDrawable.getIntrinsicHeight();
            } else if (offsetPx > 0) {
                outRect.top = offsetPx;
            }
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        if (offsetDrawable == null) {
            return;
        }

        int orientation = ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            drawOffsetHorizontal(c, parent);
        } else if (orientation == LinearLayoutManager.VERTICAL) {
            drawOffsetVertical(c, parent);
        }
    }

    private void drawOffsetHorizontal(Canvas canvas, RecyclerView parent) {
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();
        int left = parent.getPaddingLeft();
        int right = left + offsetDrawable.getIntrinsicWidth();

        offsetDrawable.setBounds(left, top, right, bottom);
        offsetDrawable.draw(canvas);
    }

    private void drawOffsetVertical(Canvas canvas, RecyclerView parent) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int top = parent.getPaddingTop();
        int bottom = top + offsetDrawable.getIntrinsicHeight();

        offsetDrawable.setBounds(left, top, right, bottom);
        offsetDrawable.draw(canvas);
    }
}
