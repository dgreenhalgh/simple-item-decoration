package com.dgreenhalgh.android.simpleitemdecorationsample.two;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dgreenhalgh.android.simpleitemdecorationsample.R;

import java.util.List;

public class SimpleItemDecorationSampleListAdapter extends RecyclerView.Adapter<SimpleListItemDecorationSampleListHolder> {

    private List<String> sampleStringList;

    public SimpleItemDecorationSampleListAdapter(List<String> sampleStringList) {
        this.sampleStringList = sampleStringList;
    }

    @Override
    public SimpleListItemDecorationSampleListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_sample, parent, false);
        return new SimpleListItemDecorationSampleListHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleListItemDecorationSampleListHolder holder, int position) {
        holder.bind(sampleStringList.get(position));
    }

    @Override
    public int getItemCount() {
        return sampleStringList.size();
    }
}
