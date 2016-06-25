package com.dgreenhalgh.android.simpleitemdecorationsample.two;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.dgreenhalgh.android.simpleitemdecorationsample.R;
import com.dgreenhalgh.android.simpleitemdecorationsample.controller.GridLayoutManagerSampleActivity;
import com.dgreenhalgh.android.simpleitemdecorationsample.controller.HorizontalLinearLayoutManagerSampleActivity;
import com.dgreenhalgh.android.simpleitemdecorationsample.controller.VerticalLinearLayoutManagerSampleActivity;


public class MainActivity extends AppCompatActivity {

    private Button verticalLinearLayoutSampleButton;
    private Button horizontalLinearLayoutSampleButton;
    private Button verticalGridLayoutSampleButton;
    private Button horizontalGridLayoutSampleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verticalLinearLayoutSampleButton = (Button) findViewById(R.id.vertical_linear_layout_manager_button);
        verticalLinearLayoutSampleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent verticalLinearLayoutManagerSampleIntent = VerticalLinearLayoutManagerSampleActivity.newIntent(MainActivity.this);
                startActivity(verticalLinearLayoutManagerSampleIntent);
            }
        });

        horizontalLinearLayoutSampleButton = (Button) findViewById(R.id.horizontal_linear_layout_manager_button);
        horizontalLinearLayoutSampleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent horizontalLinearLayoutManagerSampleIntent = HorizontalLinearLayoutManagerSampleActivity.newIntent(MainActivity.this);
                startActivity(horizontalLinearLayoutManagerSampleIntent);
            }
        });

        verticalGridLayoutSampleButton = (Button) findViewById(R.id.vertical_grid_layout_manager_button);
        verticalGridLayoutSampleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gridLayoutManagerSampleIntent = GridLayoutManagerSampleActivity.newIntent(MainActivity.this);
                startActivity(gridLayoutManagerSampleIntent);
            }
        });

        horizontalGridLayoutSampleButton = (Button) findViewById(R.id.horizontal_grid_layout_manager_button);
        horizontalGridLayoutSampleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO:
            }
        });
    }
}
