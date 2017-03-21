package com.dgreenhalgh.android.simpleitemdecoration.experimental

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Adds an offset to the end of a [RecyclerView] using a [LinearLayoutManager] or its subclass.
 */
class EndOffsetItemDecoration(val context: Context, _orientation: Int) : RecyclerView.ItemDecoration() {

    companion object {
        private val ATTRS = intArrayOf(android.R.attr.listDivider)

        val HORIZONTAL = LinearLayoutManager.HORIZONTAL
        val VERTICAL = LinearLayoutManager.VERTICAL
    }

    private var orientation: Int = VERTICAL
        set(value) {
            if (value != HORIZONTAL && value != VERTICAL) {
                throw IllegalArgumentException("Invalid orientation. It should be either HORIZONTAL or VERTICAL")
            }

            field = value
        }

    /**
     * [Drawable] to be used as a divider at the end of the [RecyclerView].
     */
    var divider: Drawable

    val bounds = Rect()

    init {
        orientation = _orientation

        val a: TypedArray = context.obtainStyledAttributes(ATTRS)
        divider = a.getDrawable(0)
        a.recycle()
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        if (parent.getChildAdapterPosition(view) < parent.adapter.itemCount - 1) {
            return
        }

        if (orientation == StartOffsetItemDecoration.VERTICAL) {
            outRect.set(0, 0, 0, divider.intrinsicHeight)
        } else {
            outRect.set(0, 0, divider.intrinsicWidth, 0)
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        if (orientation == StartOffsetItemDecoration.VERTICAL) {
            drawVertical(c, parent)
        } else {
            drawHorizontal(c, parent)
        }
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        canvas.save()

        if (isLastVisibleChildLastAdapterPosition(parent)) return

        var left = 0
        var right = parent.width

        if (parent.clipToPadding) {
            left = parent.paddingLeft
            right = parent.width - parent.paddingRight
            canvas.clipRect(left, parent.paddingTop, right, parent.height - parent.paddingBottom)
        }

        val lastChild = parent.getChildAt(parent.childCount - 1)
        parent.getDecoratedBoundsWithMargins(lastChild, bounds)
        val bottom = bounds.bottom + Math.round(ViewCompat.getTranslationY(lastChild))
        val top = bottom - divider.intrinsicHeight

        divider.setBounds(left, top, right, bottom)
        divider.draw(canvas)

        canvas.restore()
    }

    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        canvas.save()

        if (isLastVisibleChildLastAdapterPosition(parent)) return

        var top = 0
        var bottom = parent.height

        if (parent.clipToPadding) {
            top = parent.paddingTop
            bottom = parent.height - parent.paddingBottom
            canvas.clipRect(parent.paddingLeft, top, parent.width - parent.paddingRight, bottom)
        }

        val lastChild = parent.getChildAt(parent.childCount - 1)
        parent.getDecoratedBoundsWithMargins(lastChild, bounds)
        val right = bounds.right + Math.round(ViewCompat.getTranslationX(lastChild))
        val left = right - divider.intrinsicWidth

        divider.setBounds(left, top, right, bottom)
        divider.draw(canvas)

        canvas.restore()
    }

    private fun isLastVisibleChildLastAdapterPosition(parent: RecyclerView): Boolean {
        val layoutManager = parent.layoutManager as LinearLayoutManager
        val lastVisibleChildAdapterPosition = layoutManager.findLastCompletelyVisibleItemPosition()
        val lastChildAdapterPosition = parent.adapter.itemCount - 1

        return lastVisibleChildAdapterPosition > lastChildAdapterPosition
    }
}