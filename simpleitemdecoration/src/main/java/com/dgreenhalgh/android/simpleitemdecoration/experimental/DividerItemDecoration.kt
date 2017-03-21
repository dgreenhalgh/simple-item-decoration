package com.dgreenhalgh.android.simpleitemdecoration.experimental

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Adds a divider to a [RecyclerView] using a [LinearLayoutManager] or its subclass.
 * Infers orientation of parent [RecyclerView] and passes up to Android Standard
 * [DividerItemDecoration] implementation.
 */
class DividerItemDecoration(context: Context) : DividerItemDecoration(context, LinearLayoutManager.VERTICAL) {

    companion object {
        private val ATTRS = intArrayOf(android.R.attr.listDivider)
    }

    var divider: Drawable = ShapeDrawable()
        set(value) = setDrawable(value)

    init {
        val a: TypedArray = context.obtainStyledAttributes(com.dgreenhalgh.android.simpleitemdecoration.experimental.DividerItemDecoration.ATTRS)
        divider = a.getDrawable(0)
        a.recycle()
    }

    override fun getItemOffsets(outRect: Rect?, view: View, parent: RecyclerView?, state: RecyclerView.State?) {
        inferOrientation(parent!!)
        super.getItemOffsets(outRect, view, parent, state)
    }

    private fun inferOrientation(parent: RecyclerView) {
        val orientation = (parent.layoutManager as LinearLayoutManager).orientation
        setOrientation(orientation)
    }
}