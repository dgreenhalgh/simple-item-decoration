package com.dgreenhalgh.android.simpleitemdecoration;

import android.graphics.Canvas;
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

    /**
     * Draws horizontal or vertical offset onto the end of the parent
     * RecyclerView.
     *
     * @param c The {@link Canvas} onto which an offset will be drawn
     * @param parent The RecyclerView onto which an offset is being added
     * @param state The current RecyclerView.State of the RecyclerView
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (offsetDrawable == null) {
            return;
        }

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            drawOffsetForGridLayout(c, parent, state);
        } else if (layoutManager instanceof LinearLayoutManager) {
            int lastVisibleIndex = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            if (lastVisibleIndex < state.getItemCount() - 1) {
                return;
            }

            int orientation = ((LinearLayoutManager) layoutManager).getOrientation();
            if (orientation == LinearLayoutManager.HORIZONTAL) {
                drawOffsetForHorizontalLinearLayout(c, parent, state);
            } else if (orientation == LinearLayoutManager.VERTICAL) {
                drawOffsetForVerticalLinearLayout(c, parent, state);
            }
        } else {
            throw new UnsupportedOperationException("Simple ItemDecoration only supports " +
                    "LinearLayoutManager and its children.");
        }
    }

    private int getLastSpanMaxIndex(RecyclerView parent, RecyclerView.State state, int orientation) { // TODO: Remove orientation
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
        int lastRowItemCount = itemCount % spanCount;
        if (lastRowItemCount == 0) {
            lastRowItemCount = spanCount;
        }

        return lastRowItemCount;
    }

    private void drawOffsetForHorizontalLinearLayout(Canvas canvas, RecyclerView parent,
                                                     RecyclerView.State state) {
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();

        View lastChild = parent.getChildAt(state.getItemCount() - 1);
        RecyclerView.LayoutParams lastChildLayoutParams = (RecyclerView.LayoutParams) lastChild.getLayoutParams();
        int left = lastChild.getRight() + lastChildLayoutParams.rightMargin;
        int right = left + offsetDrawable.getIntrinsicWidth();

        offsetDrawable.setBounds(left, top, right, bottom);
        offsetDrawable.draw(canvas);
    }

    private void drawOffsetForVerticalLinearLayout(Canvas canvas, RecyclerView parent,
                                                   RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        View lastChild = parent.getChildAt(layoutManager.findLastVisibleItemPosition()); // TODO: No. getChildAt may not work
//        View lastChild = parent.getChildAt(state.getItemCount() - 1);
        RecyclerView.LayoutParams lastChildLayoutParams = (RecyclerView.LayoutParams) lastChild.getLayoutParams();
        int top = lastChild.getBottom() + lastChildLayoutParams.bottomMargin;
        int bottom = top + offsetDrawable.getIntrinsicHeight();

        offsetDrawable.setBounds(left, top, right, bottom);
        offsetDrawable.draw(canvas);
    }

    private void drawOffsetForGridLayout(Canvas canvas, RecyclerView parent,
                                         RecyclerView.State state) {
        int itemCount = state.getItemCount();

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (((LinearLayoutManager) layoutManager).findLastVisibleItemPosition() < itemCount) {
            return;
        }

        int spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        int orientation = ((GridLayoutManager) layoutManager).getOrientation();

        if (orientation == LinearLayoutManager.HORIZONTAL) {
            drawOffsetForHorizontalGridLayout(canvas, parent, itemCount, spanCount);
        } else if (orientation == LinearLayoutManager.VERTICAL) {
            drawOffsetForVerticalGridLayout(canvas, parent, itemCount, spanCount);
        }
    }

    private void drawOffsetForHorizontalGridLayout(Canvas canvas, RecyclerView parent,
                                                   int itemCount, int spanCount) {
        int lastRowFirstChildIndex = itemCount - getLastSpanItemCount(itemCount, spanCount);

        int top = parent.getTop();
        int bottom = top + parent.getChildAt(itemCount - 1).getBottom();

        int left = 0;
        int right = 0;

        for (int i = lastRowFirstChildIndex; i < itemCount; i++) { // TODO: Loop may be unnecessary
            View child = parent.getChildAt(i);
            left = child.getRight();
            right = left + offsetDrawable.getIntrinsicWidth();
        }



//        int left = parent.getChildAt(lastRowFirstChildIndex).getRight();
//        int right = left + offsetDrawable.getIntrinsicWidth();

        offsetDrawable.setBounds(left, top, right, bottom);
        offsetDrawable.draw(canvas);
    }

    private void drawOffsetForVerticalGridLayout(Canvas canvas, RecyclerView parent,
                                                   int itemCount, int spanCount) {
        int lastRowFirstChildIndex = itemCount - getLastSpanItemCount(itemCount, spanCount); // TODO: child vs. item consistency

        int left = parent.getPaddingLeft();
        int right = parent.getChildAt(itemCount - 1).getRight();
        int top = 0;
        int bottom = 0;

        for (int i = lastRowFirstChildIndex; i < itemCount; i++) {
            View child = parent.getChildAt(i);
            top = child.getBottom();
            bottom = top + offsetDrawable.getIntrinsicHeight();
        }

        offsetDrawable.setBounds(left, top, right, bottom);
        offsetDrawable.draw(canvas);
    }
}
