package com.dgreenhalgh.android.simpleitemdecorationsample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    private Button mVerticalLinearLayoutManagerSampleButton;
    private Button mHorizontalLinearLayoutManagerSampleButton;
    private Button mGridLayoutManagerSampleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVerticalLinearLayoutManagerSampleButton = (Button) findViewById(R.id.activity_main_verticalLinearLayoutManagerButton);
        mVerticalLinearLayoutManagerSampleButton.setOnClickListener(mVerticalLinearLayoutManagerSampleButtonClickListener);

        mHorizontalLinearLayoutManagerSampleButton = (Button) findViewById(R.id.activity_main_horizontalLinearLayoutManagerButton);
        mHorizontalLinearLayoutManagerSampleButton.setOnClickListener(mHorizontalLinearLayoutManagerSampleButtonClickListener);

        mGridLayoutManagerSampleButton = (Button) findViewById(R.id.activity_main_gridLayoutManagerButton);
        mGridLayoutManagerSampleButton.setOnClickListener(mGridLayoutManagerSampleButtonClickListener);
    }

    private View.OnClickListener mVerticalLinearLayoutManagerSampleButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Context context = view.getContext();

            Intent verticalLinearLayoutManagerSampleIntent = VerticalLinearLayoutManagerSampleActivity.newIntent(context);
            context.startActivity(verticalLinearLayoutManagerSampleIntent);
        }
    };

    private View.OnClickListener mHorizontalLinearLayoutManagerSampleButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Context context = view.getContext();

            Intent horizontalLinearLayoutManagerSampleIntent = HorizontalLinearLayoutManagerSampleActivity.newIntent(context);
            context.startActivity(horizontalLinearLayoutManagerSampleIntent);
        }
    };

    private View.OnClickListener mGridLayoutManagerSampleButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Context context = view.getContext();

            Intent gridLayoutManagerSampleIntent = GridLayoutManagerSampleActivity.newIntent(context);
            context.startActivity(gridLayoutManagerSampleIntent);
        }
    };
}
