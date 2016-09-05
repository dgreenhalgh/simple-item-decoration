package com.dgreenhalgh.android.simpleitemdecoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private boolean dividerIsDrawable;

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
        horizontalDivider = divider;
        verticalDivider = divider;
        dividerIsDrawable = true;
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
        horizontalOffsetPx = offsetPx;
        verticalOffsetPx = offsetPx;
        dividerIsDrawable = false;
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
        dividerIsDrawable = true;
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
        dividerIsDrawable = false;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (!dividerIsDrawable) {
            return;
        }

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            drawDividersForGridLayout(c, parent);
        } else if (layoutManager instanceof LinearLayoutManager) {
            int orientation = ((LinearLayoutManager) layoutManager).getOrientation();
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
        if (horizontalDivider == null) {
            return;
        }

        int top = parent.getPaddingTop();

        View firstChild = parent.getChildAt(0);
        int bottom = top + firstChild.getBottom();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int left = child.getRight() + params.rightMargin;
            int right = left + horizontalDivider.getIntrinsicHeight();

            horizontalDivider.setBounds(left, top, right, bottom);
            horizontalDivider.draw(canvas);
        }
    }

    private void drawDividersForVerticalLinearLayout(Canvas canvas, RecyclerView parent) {
        if (verticalDivider == null) {
            return;
        }

        int left = parent.getPaddingLeft();

        View firstChild = parent.getChildAt(0);
        int right = left + firstChild.getRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + verticalDivider.getIntrinsicHeight();

            verticalDivider.setBounds(left, top, right, bottom);
            verticalDivider.draw(canvas);
        }
    }

    private void drawDividersForGridLayout(Canvas canvas, RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int orientation = ((GridLayoutManager) layoutManager).getOrientation();

        int childCount = parent.getChildCount();
        int spanCount = ((GridLayoutManager) layoutManager).getSpanCount();

        if (orientation == LinearLayoutManager.HORIZONTAL) {
            drawHorizontalDividersForHorizontalGridLayout(canvas, parent, childCount, spanCount);
            drawVerticalDividersForHorizontalGridLayout(canvas, parent, childCount, spanCount);
        } else if (orientation == LinearLayoutManager.VERTICAL) {
            drawHorizontalDividersForVerticalGridLayout(canvas, parent, childCount, spanCount);
            drawVerticalDividersForVerticalGridLayout(canvas, parent, childCount, spanCount);
        }
    }

    private void drawHorizontalDividersForHorizontalGridLayout(Canvas canvas, RecyclerView parent,
                                                               int childCount, int spanCount) {
        if (horizontalDivider == null) {
            return;
        }

        int columnCount = (int) Math.ceil((double) childCount / (double) spanCount);

        for (int i = spanCount; i < spanCount * columnCount; i += spanCount) {
            int lastRowChildIndex;

            if (i < spanCount * (columnCount - 1)) {
                lastRowChildIndex = i + spanCount - 1;
            } else {
                lastRowChildIndex = i + spanCount - 2;
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

    private void drawVerticalDividersForHorizontalGridLayout(Canvas canvas, RecyclerView parent,
                                                             int childCount, int spanCount) {
        if (verticalDivider == null) {
            return;
        }

        int columnCount = (int) Math.ceil((double) childCount / (double) spanCount);
        int rightmostChildIndex;

        for (int i = 1; i < spanCount; i++) {
            if (i == spanCount - 1) {
                rightmostChildIndex = parent.getChildCount() - spanCount - 1;
            } else {
                rightmostChildIndex = i + (spanCount * (columnCount - 1));
            }

            View leftmostChild = parent.getChildAt(i);
            View rightmostChild = parent.getChildAt(rightmostChildIndex);

            int left = leftmostChild.getLeft();
            int bottom = leftmostChild.getTop();
            int top = bottom - verticalDivider.getIntrinsicHeight();
            int right = rightmostChild.getRight();

            verticalDivider.setBounds(left, top, right, bottom);
            verticalDivider.draw(canvas);
        }
    }

    private void drawHorizontalDividersForVerticalGridLayout(Canvas canvas, RecyclerView parent,
                                                             int childCount, int spanCount) {
        if (horizontalDivider == null) {
            return;
        }

        int rowCount = childCount / spanCount;
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

    private void drawVerticalDividersForVerticalGridLayout(Canvas canvas, RecyclerView parent,
                                                           int childCount, int spanCount) {
        if (verticalDivider == null) {
            return;
        }

        int rightmostChildIndex;
        int rowCount = childCount / spanCount;

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

        int orientation = ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            if (dividerIsDrawable) {
                if (horizontalDivider != null) {
                    outRect.left = horizontalDivider.getIntrinsicWidth();
                }
            } else {
                outRect.left = horizontalOffsetPx;
            }
        } else if (orientation == LinearLayoutManager.VERTICAL) {
            if (dividerIsDrawable) {
                if (verticalDivider != null) {
                    outRect.top = verticalDivider.getIntrinsicHeight();
                }
            } else {
                outRect.top = verticalOffsetPx;
            }
        }
    }

    private void getItemOffsetsForGridLayout(Rect outRect, View view, RecyclerView parent, int spanCount) {
        int orientation = ((GridLayoutManager) parent.getLayoutManager()).getOrientation();
        boolean childIsInFirstColumn = false;
        boolean childIsInFirstRow = false;

        if (orientation == LinearLayoutManager.HORIZONTAL) {
            int childCount = parent.getChildCount();
            childIsInFirstColumn = childCount <= spanCount;
            childIsInFirstRow = ((childCount - 1) % spanCount) == 0;
        } else if (orientation == LinearLayoutManager.VERTICAL) {
            childIsInFirstColumn = (parent.getChildAdapterPosition(view) % spanCount) == 0;
            childIsInFirstRow = (parent.getChildAdapterPosition(view)) < spanCount;
        }

        if (!childIsInFirstColumn) {
            if (dividerIsDrawable) {
                if (horizontalDivider != null) {
                    outRect.left = horizontalDivider.getIntrinsicWidth();
                }
            } else {
                outRect.left = horizontalOffsetPx;
            }
        }

        if (!childIsInFirstRow) {
            if (dividerIsDrawable) {
                if (verticalDivider != null) {
                    outRect.top = verticalDivider.getIntrinsicHeight();
                }
            } else {
                outRect.top = verticalOffsetPx;
            }
        }
    }
}
