package com.dgreenhalgh.android.simpleitemdecorationsample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.dgreenhalgh.android.simpleitemdecoration.DividerItemDecoration;
import com.dgreenhalgh.android.simpleitemdecoration.GridDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class GridLayoutManagerSampleActivity extends Activity {

    private static final int NUM_COLUMNS = 3;

    private CheckBox mDividersCheckBox;
    private RecyclerView mRecyclerView;
    private RecyclerView.ItemDecoration mDividerItemDecoration;

    private boolean mDividersVisible;

    public static Intent newIntent(Context context) {
        return new Intent(context, GridLayoutManagerSampleActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_layout_manager_sample);

        mDividersCheckBox = (CheckBox) findViewById(R.id.activity_grid_layout_manager_sample_dividersCheckBox);
        mDividersCheckBox.setOnCheckedChangeListener(mDividersCheckBoxCheckedChangeListener);

        mRecyclerView = (RecyclerView) findViewById(R.id.activity_grid_layout_manager_sample_recyclerView);
        initRecyclerView();
    }

    private CompoundButton.OnCheckedChangeListener mDividersCheckBoxCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            toggleDividerVisibility();
        }
    };

    private void initRecyclerView() {
        List<String> sampleStringList = getSampleData();

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, NUM_COLUMNS));
        mRecyclerView.setAdapter(new SimpleItemDecorationSampleListAdapter(sampleStringList));

        Drawable dividerDrawable = getResources().getDrawable(R.drawable.divider_sample);
        mDividerItemDecoration = new GridDividerItemDecoration(dividerDrawable, dividerDrawable, NUM_COLUMNS);
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
