package com.dgreenhalgh.android.simpleitemdecorationsample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.dgreenhalgh.android.simpleitemdecoration.DividerItemDecoration;
import com.dgreenhalgh.android.simpleitemdecoration.StartOffsetItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class VerticalLinearLayoutManagerSampleActivity extends Activity {

    private CheckBox mDividersCheckBox;
    private CheckBox mStartOffsetCheckBox;
    private RecyclerView mRecyclerView;

    private RecyclerView.ItemDecoration mDividerItemDecoration;
    private RecyclerView.ItemDecoration mStartOffsetItemDecoration;

    private boolean mDividersVisible;
    private boolean mStartOffsetVisible;

    public static Intent newIntent(Context context) {
        return new Intent(context, VerticalLinearLayoutManagerSampleActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_linear_layout_manager_sample);

        mDividersCheckBox = (CheckBox) findViewById(R.id.activity_vertical_linear_layout_manager_sample_dividersCheckBox);
        mDividersCheckBox.setOnCheckedChangeListener(mDividersCheckBoxCheckedChangeListener);

        mStartOffsetCheckBox = (CheckBox) findViewById(R.id.activity_vertical_linear_layout_manager_sample_startOffsetCheckBox);
        mStartOffsetCheckBox.setOnCheckedChangeListener(mStartOffsetCheckBoxCheckedChangeListener);

        mRecyclerView = (RecyclerView) findViewById(R.id.activity_vertical_linear_layout_manager_sample_recyclerView);
        initRecyclerView();
    }

    private CompoundButton.OnCheckedChangeListener mDividersCheckBoxCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            toggleDividerVisibility();
        }
    };

    private CompoundButton.OnCheckedChangeListener mStartOffsetCheckBoxCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            toggleStartOffsetVisibility();
        }
    };

    private void initRecyclerView() {
        List<String> sampleStringList = getSampleData();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new SimpleItemDecorationSampleListAdapter(sampleStringList));

        Resources resources = getResources();
        Drawable dividerDrawable = resources.getDrawable(R.drawable.divider_sample);
        mDividerItemDecoration = new DividerItemDecoration(dividerDrawable);

        int startOffsetPx = resources.getDimensionPixelOffset(R.dimen.start_offset);
        mStartOffsetItemDecoration = new StartOffsetItemDecoration(startOffsetPx);
    }

    private void toggleDividerVisibility() {
        if (mDividersVisible) {
            mRecyclerView.removeItemDecoration(mDividerItemDecoration);
            mDividersVisible = false;
        } else {
            mRecyclerView.addItemDecoration(mDividerItemDecoration);
            mDividersVisible = true;
        }
    }

    private void toggleStartOffsetVisibility() {
        if (mStartOffsetVisible) {
            mRecyclerView.removeItemDecoration(mStartOffsetItemDecoration);
            mStartOffsetVisible = false;
        } else {
            mRecyclerView.addItemDecoration(mStartOffsetItemDecoration);
            mStartOffsetVisible = true;
        }
    }

    private List<String> getSampleData() {
        List<String> sampleStringList = new ArrayList<>();
        sampleStringList.add("Paul");
        sampleStringList.add("David");
        sampleStringList.add("Kristin");
        sampleStringList.add("Chris");
        sampleStringList.add("Josh");
        sampleStringList.add("Andrew");
        sampleStringList.add("Brian");
        sampleStringList.add("Matt");
        sampleStringList.add("Bill");
        sampleStringList.add("Jason");
        sampleStringList.add("Bolot");
        sampleStringList.add("Sean");

        return sampleStringList;
    }
}
