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
class StartOffsetItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    companion object {
        private val ATTRS = intArrayOf(android.R.attr.listDivider)
    }

    private var orientation: Int = LinearLayoutManager.VERTICAL

    /**
     * [Drawable] to be used as a divider at the start of the [RecyclerView].
     */
    var divider: Drawable

    init {
        val a: TypedArray = context.obtainStyledAttributes(ATTRS)
        divider = a.getDrawable(0)
        a.recycle()
    }

    override fun getItemOffsets(outRect: Rect?, view: View, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)

        if (parent == null) return

        if (parent.getChildAdapterPosition(view) > 0) {
            return
        }

        inferOrientation(parent)

        if (orientation == LinearLayoutManager.VERTICAL) {
            outRect?.set(0, divider.intrinsicHeight, 0, 0)
        } else {
            outRect?.set(divider.intrinsicWidth, 0, 0, 0)
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
        val top = 0
        val right = parent.width
        val bottom = divider.intrinsicHeight

        divider.setBounds(left, top, right, bottom)
        divider.draw(canvas)

        canvas.restore()
    }

    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        canvas.save()

        val left = 0
        val top = 0
        val right = divider.intrinsicWidth
        val bottom = parent.height

        divider.setBounds(left, top, right, bottom)
        divider.draw(canvas)

        canvas.restore()
    }
}