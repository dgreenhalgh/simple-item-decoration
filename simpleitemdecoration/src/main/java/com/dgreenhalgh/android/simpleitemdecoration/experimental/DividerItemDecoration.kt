package com.dgreenhalgh.android.simpleitemdecoration.experimental

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v4.view.ViewCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Adds a divider to a [RecyclerView] using a [LinearLayoutManager] or its subclass.
 * Infers orientation of parent [RecyclerView] and passes up to Android Standard
 * [DividerItemDecoration] implementation.
 */
class DividerItemDecoration(context: Context) : RecyclerView.ItemDecoration() { // TODO: Root Simple class?

    companion object {
        private val ATTRS = intArrayOf(android.R.attr.listDivider)
    }

    private var orientation: Int = LinearLayoutManager.VERTICAL

    /**
     * [Drawable] to be used as a divider at the end of the [RecyclerView].
     */
    var divider: Drawable

    val bounds = Rect()

    init {
        val a: TypedArray = context.obtainStyledAttributes(com.dgreenhalgh.android.simpleitemdecoration.experimental.DividerItemDecoration.ATTRS)
        divider = a.getDrawable(0)
        a.recycle()
    }

    override fun getItemOffsets(outRect: Rect?, view: View, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)

        if (parent == null) return

        if (parent.getChildAdapterPosition(view) == parent.adapter.itemCount - 1) {
            return
        }

        inferOrientation(parent)

        if (orientation == LinearLayoutManager.VERTICAL) {
            outRect?.set(0, 0, 0, divider.intrinsicHeight)
        } else {
            outRect?.set(0, 0, divider.intrinsicWidth, 0)
        }
    }

    override fun onDraw(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.onDraw(c, parent, state)

        if (orientation == LinearLayoutManager.VERTICAL) {
            drawVertical(c!!, parent!!)
        } else {
            drawHorizontal(c!!, parent!!)
        }
    }

    private fun inferOrientation(parent: RecyclerView) {
        orientation = (parent.layoutManager as LinearLayoutManager).orientation
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        canvas.save()

        val left = 0
        val right = parent.width

        for (childIndex in 0..parent.childCount - 1) {
            val child = parent.getChildAt(childIndex)
            parent.getDecoratedBoundsWithMargins(child, bounds)

            val bottom = bounds.bottom + Math.round(ViewCompat.getTranslationY(child))
            val top = bottom - divider.intrinsicHeight

            divider.setBounds(left, top, right, bottom)
            divider.draw(canvas)
        }

        canvas.restore()
    }

    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        canvas.save()

        val top = 0
        val bottom = parent.height

        for (childIndex in 0..parent.childCount - 1) {
            val child = parent.getChildAt(childIndex)
            parent.getDecoratedBoundsWithMargins(child, bounds)

            val right = bounds.right + Math.round(ViewCompat.getTranslationX(child))
            val left = right - divider.intrinsicWidth

            divider.setBounds(left, top, right, bottom)
            divider.draw(canvas)
        }

        canvas.restore()
    }
}