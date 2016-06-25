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

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager(); // TODO: Test scroll direction
        if (layoutManager instanceof GridLayoutManager) {
            if (horizontalDivider == null
                    || verticalDivider == null) { // TODO: Handle individual null case
                return;
            }

            drawDividersForGridLayout(c, parent);
        } else if (layoutManager instanceof LinearLayoutManager) {
            if (divider == null) {
                return;
            }

            int orientation = ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
            if (orientation == LinearLayoutManager.HORIZONTAL) {
                drawDividersForHorizontalLinearLayout(c, parent);
            } else if (orientation == LinearLayoutManager.VERTICAL) {
                drawDividersForVerticalLinearLayout(c, parent);
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

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
            getItemOffsetsForGridLayout(outRect, view, parent, spanCount);
        } else if (layoutManager instanceof LinearLayoutManager) {
            getItemOffsetsForLinearLayout(outRect, view, parent);
        } else {
            throw new UnsupportedOperationException("Simple ItemDecoration only supports " +
                    "LinearLayoutManager and its children.");
        }
    }

    private void drawDividersForHorizontalLinearLayout(Canvas canvas, RecyclerView parent) {
        int top = parent.getPaddingTop();

        View firstChild = parent.getChildAt(0);
        int bottom = top + firstChild.getBottom(); // TODO: Should be plus top here?

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

    private void drawDividersForVerticalLinearLayout(Canvas canvas, RecyclerView parent) {
        int left = parent.getPaddingLeft();

        View firstChild = parent.getChildAt(0);
        int right = left + firstChild.getRight(); // TODO: Should be plus right here?

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

    private void drawDividersForGridLayout(Canvas canvas, RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int orientation = ((GridLayoutManager) layoutManager).getOrientation();

        int childCount = parent.getChildCount();
        int spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        int rowCount = childCount / spanCount;

        if (orientation == LinearLayoutManager.VERTICAL) {
            drawHorizontalDividersForVerticalGridLayout(canvas, parent, childCount, rowCount, spanCount);
            drawVerticalDividersForVerticalGridLayout(canvas, parent, rowCount, spanCount); // todo span count and row count behave differently depending on scroll position
        } else if (orientation == LinearLayoutManager.HORIZONTAL) {
            // TODO: drawHorizontalDividersForHorizontalGridLayout
            // TODO: drawHorizontalDividersForVerticalGridLayout
        }
    }

    private void drawHorizontalDividersForVerticalGridLayout(Canvas canvas, RecyclerView parent,
                                                             int childCount, int rowCount, int spanCount) {
        int lastRowChildCount = childCount % spanCount;

        for (int i = 1; i < spanCount; i++) {
            int lastRowChildIndex;
            if (i < lastRowChildCount) {
                lastRowChildIndex = i + (rowCount * spanCount);
            } else {
                lastRowChildIndex = i + ((rowCount - 1) * spanCount);
            }

            View firstRowChild = parent.getChildAt(i);
            View lastRowChild = parent.getChildAt(lastRowChildIndex);

            int top = firstRowChild.getTop();
            int right = firstRowChild.getLeft();
            int left = right - horizontalDivider.getIntrinsicHeight();
            int bottom = lastRowChild.getBottom();

            horizontalDivider.setBounds(left, top, right, bottom);
            horizontalDivider.draw(canvas);
        }
    }

//    private void drawHorizontalDividersForHorizontalGridLayout(Canvas canvas, RecyclerView parent,
//                                                             int childCount, int rowCount, int spanCount) {
//        int lastRowChildCount = // todo
//
//        for (int i = 1; i < spanCount; i++) {
//            // todo
//
//            View firstRowChild = parent.getChildAt(i);
//            View lastRowChild = parent.getChildAt(lastRowChildIndex);
//
//            int top = firstRowChild.getTop();
//            int right = firstRowChild.getLeft();
//            int left = right - horizontalDivider.getIntrinsicHeight();
//            int bottom = lastRowChild.getBottom();
//
//            horizontalDivider.setBounds(left, top, right, bottom);
//            horizontalDivider.draw(canvas);
//        }
//    }

    private void drawVerticalDividersForVerticalGridLayout(Canvas canvas, RecyclerView parent, int rowCount,
                                                           int spanCount) {
        int rightmostChildIndex;

        for (int i = 1; i <= rowCount; i++) {
            if (i == rowCount) {
                rightmostChildIndex = parent.getChildCount() - 1;
            } else {
                rightmostChildIndex = (i * spanCount) + spanCount - 1;
            }

            View leftmostChild = parent.getChildAt(i * spanCount);
            View rightmostChild = parent.getChildAt(rightmostChildIndex);

            int left = leftmostChild.getLeft();
            int bottom = leftmostChild.getTop();
            int top = bottom - verticalDivider.getIntrinsicHeight();
            int right = rightmostChild.getRight();

            verticalDivider.setBounds(left, top, right, bottom);
            verticalDivider.draw(canvas);
        }
    }

    private void getItemOffsetsForLinearLayout(Rect outRect, View view, RecyclerView parent) {
        if (parent.getChildAdapterPosition(view) == 0) {
            return;
        }

        int orientation = ((LinearLayoutManager) parent.getLayoutManager()).getOrientation(); // TODO: Set divider to hdiv or vdiv instead?
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            if (offsetPx != 0) {
                outRect.left = offsetPx;
            } else if (divider != null) {
                outRect.left = divider.getIntrinsicWidth();
            }
        } else if (orientation == LinearLayoutManager.VERTICAL) {
            if (offsetPx != 0) {
                outRect.top = offsetPx;
            } else if (divider != null) {
                outRect.top = divider.getIntrinsicHeight();
            }
        }
    }

    private void getItemOffsetsForGridLayout(Rect outRect, View view, RecyclerView parent, int spanCount) {
        boolean childIsInFirstColumn = (parent.getChildAdapterPosition(view) % spanCount) == 0;
        if (!childIsInFirstColumn) {
            if (horizontalOffsetPx != 0) {
                outRect.left = horizontalOffsetPx;
            } else if (horizontalDivider != null) {
                outRect.left = horizontalDivider.getIntrinsicWidth();
            }
        }

        boolean childIsInFirstRow = (parent.getChildAdapterPosition(view)) < spanCount;
        if (!childIsInFirstRow) {
            if (verticalOffsetPx != 0) {
                outRect.top = verticalOffsetPx;
            } else if (verticalDivider != null) {
                outRect.top = verticalDivider.getIntrinsicHeight();
            }
        }
    }
}
