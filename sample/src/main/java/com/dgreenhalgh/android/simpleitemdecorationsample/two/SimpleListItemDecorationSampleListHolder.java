package com.dgreenhalgh.android.simpleitemdecorationsample.two;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class SimpleListItemDecorationSampleListHolder extends RecyclerView.ViewHolder {

    private TextView textView;

    public SimpleListItemDecorationSampleListHolder(View itemView) {
        super(itemView);

        textView = (TextView) itemView;
    }

    public void bind(String sampleText) {
        textView.setText(sampleText);
    }
}
