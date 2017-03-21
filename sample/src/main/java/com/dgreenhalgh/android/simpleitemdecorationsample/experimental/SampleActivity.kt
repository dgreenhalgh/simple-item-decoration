package com.dgreenhalgh.android.simpleitemdecorationsample.experimental

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.dgreenhalgh.android.simpleitemdecoration.experimental.DividerItemDecoration
import com.dgreenhalgh.android.simpleitemdecoration.experimental.EndOffsetItemDecoration
import com.dgreenhalgh.android.simpleitemdecoration.experimental.StartOffsetItemDecoration
import com.dgreenhalgh.android.simpleitemdecorationsample.R
import com.dgreenhalgh.android.simpleitemdecorationsample.experimental.options.DecorType
import java.util.*

class SampleActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DECOR_TYPE = "DecorType"
        const val EXTRA_ORIENTATION = "Orientation"

        fun newIntent(context: Context, decorType: List<String>, orientation: Int): Intent {
            val intent = Intent(context, SampleActivity::class.java)
            intent.putStringArrayListExtra(EXTRA_DECOR_TYPE, ArrayList(decorType))
            intent.putExtra(EXTRA_ORIENTATION, orientation)
            return intent
        }
    }

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        recyclerView = findViewById(R.id.recycler_view) as RecyclerView
        recyclerView.adapter = SampleAdapter(SampleDataBank.getSampleData())

        val orientation = setOrientation()

        val decorTypes = intent.getStringArrayListExtra(EXTRA_DECOR_TYPE)
        for (decorType in decorTypes) {
            var itemDecoration: RecyclerView.ItemDecoration = DividerItemDecoration(this, orientation)

            when (decorType) {
                DecorType.DIVIDER.name -> {
                    itemDecoration = DividerItemDecoration(this, orientation)
                    itemDecoration.divider = ContextCompat.getDrawable(this, R.drawable.divider_sample)
                }
                DecorType.START_OFFSET.name -> {
                    itemDecoration = StartOffsetItemDecoration(this, orientation)
                    itemDecoration.divider = ContextCompat.getDrawable(this, R.drawable.divider_sample)
                }
                DecorType.END_OFFSET.name -> {
                    itemDecoration = EndOffsetItemDecoration(this, orientation)
                    itemDecoration.divider = ContextCompat.getDrawable(this, R.drawable.divider_sample)
                }
            }

            recyclerView.addItemDecoration(itemDecoration)
        }
    }

    private fun setOrientation(): Int {
        val orientation = intent.getIntExtra(EXTRA_ORIENTATION, LinearLayoutManager.VERTICAL)
        recyclerView.layoutManager = LinearLayoutManager(this, orientation, false)

        return orientation
    }
}
