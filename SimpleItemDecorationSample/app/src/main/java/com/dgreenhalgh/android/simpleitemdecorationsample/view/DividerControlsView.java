package com.dgreenhalgh.android.simpleitemdecorationsample.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.dgreenhalgh.android.simpleitemdecorationsample.R;

public class DividerControlsView extends LinearLayout {

    private CheckBox mDividersCheckBox;
    private CheckBox mStartOffsetCheckBox;
    private CheckBox mEndOffsetCheckBox;

    private OnVisibilityChangeListener mOnVisibilityChangeListener;

    public DividerControlsView(Context context) {
        this(context, null);
    }

    public DividerControlsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_divider_controls, this);
        setOrientation(VERTICAL);

        mDividersCheckBox = (CheckBox) findViewById(R.id.view_divider_controls_dividersCheckBox);
        mDividersCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mOnVisibilityChangeListener.onDividerVisibilityChange();
            }
        });

        mStartOffsetCheckBox = (CheckBox) findViewById(R.id.view_divider_controls_startOffsetCheckBox);
        mStartOffsetCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mOnVisibilityChangeListener.onStartOffsetVisibilityChange();
            }
        });

        mEndOffsetCheckBox = (CheckBox) findViewById(R.id.view_divider_controls_endOffsetCheckBox);
        mEndOffsetCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mOnVisibilityChangeListener.onEndOffsetVisibilityChange();
            }
        });
    }

    public void setOnVisibilityChangeListener(OnVisibilityChangeListener onVisibilityChangeListener) {
        mOnVisibilityChangeListener = onVisibilityChangeListener;
    }

    public interface OnVisibilityChangeListener {
        void onDividerVisibilityChange();
        void onStartOffsetVisibilityChange();
        void onEndOffsetVisibilityChange();
    }
}
