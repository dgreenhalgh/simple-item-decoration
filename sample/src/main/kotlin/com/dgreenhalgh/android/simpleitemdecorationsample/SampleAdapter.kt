package com.dgreenhalgh.android.simpleitemdecorationsample

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.dgreenhalgh.android.simpleitemdecorationsample.R

class SampleAdapter(val sampleStringList: List<String>) : RecyclerView.Adapter<SampleHolder>() {

    override fun getItemCount(): Int {
        return sampleStringList.size
    }

    override fun onBindViewHolder(holder: SampleHolder?, position: Int) {
        holder?.bind(sampleStringList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SampleHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.list_item_sample, parent, false)
        return SampleHolder(view)
    }
}