package com.dgreenhalgh.android.simpleitemdecorationsample.experimental

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.dgreenhalgh.android.simpleitemdecoration.experimental.StartOffsetItemDecoration
import com.dgreenhalgh.android.simpleitemdecorationsample.R
import com.dgreenhalgh.android.simpleitemdecorationsample.two.SampleDataBank

class SampleActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        recyclerView = findViewById(R.id.recycler_view) as RecyclerView
        recyclerView.adapter = SampleAdapter(SampleDataBank.getSampleData()) // TODO: Replace
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        val soid = StartOffsetItemDecoration(this, StartOffsetItemDecoration.HORIZONTAL)
        soid.drawable = ContextCompat.getDrawable(this, R.drawable.divider_sample)
        recyclerView.addItemDecoration(soid)
    }
}
