package com.dgreenhalgh.android.simpleitemdecoration.experimental

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Adds an offset to the start of a [RecyclerView] using a [LinearLayoutManager] or its subclass.
 */
class StartOffsetItemDecoration(val context: Context, _orientation: Int) : RecyclerView.ItemDecoration() {

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
     * [Drawable] to be used as a divider at the start of the [RecyclerView].
     */
    var drawable: Drawable

    init {
        orientation = _orientation

        val a: TypedArray = context.obtainStyledAttributes(ATTRS)
        drawable = a.getDrawable(0)
        a.recycle()
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        if (parent.getChildAdapterPosition(view) > 0) {
            return
        }

        if (orientation == VERTICAL) {
            outRect.set(0, drawable.intrinsicHeight, 0, 0)
        } else {
            outRect.set(drawable.intrinsicWidth, 0, 0, 0)
        }
    }

    override fun onDraw(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.onDraw(c, parent, state)
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {

    }

    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {

    }
}