package com.dgreenhalgh.android.simpleitemdecorationsample.experimental

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

class SampleHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    private lateinit var textView: TextView

    init {
        textView = itemView as TextView
    }

    fun bind(text: String) {
        textView.text = text
    }
}