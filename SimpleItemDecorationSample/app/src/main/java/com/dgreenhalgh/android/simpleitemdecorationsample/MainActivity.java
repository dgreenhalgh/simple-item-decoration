package com.dgreenhalgh.android.simpleitemdecorationsample;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dgreenhalgh.android.simpleitemdecoration.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.activity_main_recyclerView);
        initRecyclerView();
    }

    private void initRecyclerView() {
        List<String> sampleStringList = getSampleData();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new SimpleItemDecorationSampleListAdapter(sampleStringList));

        addDividers();
    }

    private void addDividers() {
        Drawable dividerDrawable = getResources().getDrawable(R.drawable.divider_sample);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));
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
