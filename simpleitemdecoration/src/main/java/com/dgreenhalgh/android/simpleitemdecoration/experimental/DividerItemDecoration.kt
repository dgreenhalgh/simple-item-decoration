package com.dgreenhalgh.android.simpleitemdecoration.experimental

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager


class DividerItemDecoration(context: Context, orientation: Int) : DividerItemDecoration(context, orientation) {

    companion object { // TODO: Pull out
        private val ATTRS = intArrayOf(android.R.attr.listDivider)

        val HORIZONTAL = LinearLayoutManager.HORIZONTAL
        val VERTICAL = LinearLayoutManager.VERTICAL
    }

    var divider: Drawable = ShapeDrawable()
        set(value) = setDrawable(value)

    init {
        val a: TypedArray = context.obtainStyledAttributes(com.dgreenhalgh.android.simpleitemdecoration.experimental.DividerItemDecoration.ATTRS)
        divider = a.getDrawable(0)
        a.recycle()
    }
} // TODO: Pass up inferred orientation?