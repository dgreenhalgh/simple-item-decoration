package com.dgreenhalgh.android.simpleitemdecoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivider;
    private boolean mRecyclerViewIsLinear;
    private int mOrientation;

    private Drawable mHorizontalDivider;
    private Drawable mVerticalDivider;
    private int mNumColumns;

    /**
     * Adds dividers to a RecyclerView with a LinearLayoutManager.
     *
     * @param divider A divider Drawable to be drawn on the RecyclerView
     */
    public DividerItemDecoration(Drawable divider) {
        mDivider = divider;
    }

    /**
     * Adds dividers to a RecyclerView with a GridLayoutManager.
     *
     * @param horizontalDivider A divider Drawable to be drawn on the rows of
     *                          the grid of the RecyclerView
     * @param verticalDivider A divider Drawable to be drawn on the columns of
     *                        the grid of the RecyclerView
     * @param numColumns The number of columns in the grid of the RecyclerView
     */
    public DividerItemDecoration(Drawable horizontalDivider, Drawable verticalDivider, int numColumns) {
        mHorizontalDivider = horizontalDivider;
        mVerticalDivider = verticalDivider;
        mNumColumns = numColumns;
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        if (mRecyclerViewIsLinear) {
            drawLinearDividers(canvas, parent);
        } else {
            drawGridDividers(canvas, parent);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        mRecyclerViewIsLinear = parent.getLayoutManager() instanceof LinearLayoutManager;
        if (mRecyclerViewIsLinear) {
            mOrientation = ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
            if (mOrientation == LinearLayoutManager.HORIZONTAL) {
                outRect.left = mDivider.getIntrinsicWidth();
            } else if (mOrientation == LinearLayoutManager.VERTICAL) {
                outRect.top = mDivider.getIntrinsicHeight();
            }

            return;
        }

        boolean childIsInLeftmostColumn = (parent.getChildAdapterPosition(view) % mNumColumns) == 0;
        if(!childIsInLeftmostColumn) {
            outRect.left = mHorizontalDivider.getIntrinsicWidth();
        }

        outRect.top = mVerticalDivider.getIntrinsicHeight();
    }

    private void drawLinearDividers(Canvas canvas, RecyclerView parent) {
        if (mOrientation == LinearLayoutManager.HORIZONTAL) {
            drawHorizontalDividers(canvas, parent);
        } else if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawVerticalDividers(canvas, parent);
        }
    }

    /**
     * Adds dividers to a RecyclerView with a LinearLayoutManager (or its
     * subclass) oriented horizontally.
     */
    private void drawHorizontalDividers(Canvas canvas, RecyclerView parent) {
        int parentTop = parent.getPaddingTop();
        int parentBottom = parent.getHeight() - parent.getPaddingBottom();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int parentLeft = child.getRight() + params.rightMargin;
            int parentRight = parentLeft + mDivider.getIntrinsicWidth();

            mDivider.setBounds(parentLeft, parentTop, parentRight, parentBottom);
            mDivider.draw(canvas);
        }
    }

    /**
     * Adds dividers to a RecyclerView with a LinearLayoutManager (or its
     * subclass) oriented vertically.
     */
    private void drawVerticalDividers(Canvas canvas, RecyclerView parent) {
        int parentLeft = parent.getPaddingLeft();
        int parentRight = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int parentTop = child.getBottom() + params.bottomMargin;
            int parentBottom = parentTop + mDivider.getIntrinsicHeight();

            mDivider.setBounds(parentLeft, parentTop, parentRight, parentBottom);
            mDivider.draw(canvas);
        }
    }

    private void drawGridDividers(Canvas canvas, RecyclerView parent) {
        drawHorizontalDividersForGrid(canvas, parent);
        drawVerticalDividersForGrid(canvas, parent);
    }

    /**
     * Adds horizontal dividers to a RecyclerView with a GridLayoutManager (or
     * its subclass).
     */
    private void drawHorizontalDividersForGrid(Canvas canvas, RecyclerView parent) {
        int parentTop = parent.getPaddingTop();
        int parentBottom = parent.getHeight() - parent.getPaddingBottom();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            boolean childIsInLastColumn = (i + 1) % mNumColumns == 0;
            if (!childIsInLastColumn) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int parentLeft = child.getRight() + params.rightMargin;
                int parentRight = parentLeft + mHorizontalDivider.getIntrinsicWidth();

                mHorizontalDivider.setBounds(parentLeft, parentTop, parentRight, parentBottom);
                mHorizontalDivider.draw(canvas);
            }
        }
    }

    /**
     * Adds vertical dividers to a RecyclerView with a GridLayoutManager (or
     * its subclass).
     */
    private void drawVerticalDividersForGrid(Canvas canvas, RecyclerView parent) {
        int parentLeft = parent.getPaddingLeft();
        int parentRight = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        int numChildrenOnLastRow = childCount % mNumColumns;
        int childCountMinusLastRow = childCount - numChildrenOnLastRow;
        for (int i = 0; i < childCountMinusLastRow; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int parentTop = child.getBottom() + params.bottomMargin;
            int parentBottom = parentTop + mVerticalDivider.getIntrinsicHeight();

            mVerticalDivider.setBounds(parentLeft, parentTop, parentRight, parentBottom);
            mVerticalDivider.draw(canvas);
        }
    }
}
