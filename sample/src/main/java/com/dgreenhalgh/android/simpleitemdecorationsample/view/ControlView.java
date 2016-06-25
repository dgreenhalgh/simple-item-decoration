package com.dgreenhalgh.android.simpleitemdecorationsample.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.dgreenhalgh.android.simpleitemdecorationsample.R;

public class ControlView extends LinearLayout {

    private static final int POSITION_LINEAR_LAYOUT_MANAGER = 0;
    private static final int POSITION_GRID_LAYOUT_MANAGER = 1;
    private static final int POSITION_HORIZONTAL = 0;
    private static final int POSITION_VERTICAL = 1;
    private static final int POSITION_DRAWABLE = 0;
    private static final int POSITION_PIXEL = 1;

    private CheckBox dividersCheckBox;
    private CheckBox startOffsetCheckBox;
//    private CheckBox startDrawableOffsetCheckBox;
    private CheckBox endOffsetCheckBox;
//    private CheckBox endDrawableOffsetCheckBox;

    private Spinner layoutManagerSpinner;
    private Spinner orientationSpinner;
    private Spinner offsetTypeSpinner;

    private ItemDecorationVisibilityChangeListener itemDecorationVisibilityChangeListener;
    private LayoutManagerChangeListener layoutManagerChangeListener;
    private OrientationChangeListener orientationChangeListener;
    private OffsetTypeChangeListener offsetTypeChangeListener;

    public interface ItemDecorationVisibilityChangeListener { // TODO: name change
        void onDividerVisibilityChange();
        void onStartOffsetVisibilityChange();
        //        void onStartDrawableOffsetVisibilityChange();
        void onEndOffsetVisibilityChange();
//        void onEndDrawableOffsetVisibilityChange();
    }

    public interface LayoutManagerChangeListener {
        void onLinearLayoutManagerSelected();
        void onGridLayoutManagerSelected();
    }

    public interface OrientationChangeListener {
        void onHorizontalSelected();
        void onVerticalSelected();
    }

    public interface OffsetTypeChangeListener {
        void onDrawableSelected();
        void onPixelSelected();
    }

    public ControlView(Context context) {
        this(context, null);
    }

    public ControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_control, this);
        setOrientation(VERTICAL);

        dividersCheckBox = (CheckBox) findViewById(R.id.dividers_check_box);
        dividersCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                itemDecorationVisibilityChangeListener.onDividerVisibilityChange();
            }
        });

        startOffsetCheckBox = (CheckBox) findViewById(R.id.start_offset_check_box);
        startOffsetCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                itemDecorationVisibilityChangeListener.onStartOffsetVisibilityChange();
            }
        });

//        startDrawableOffsetCheckBox = (CheckBox) findViewById(R.id.start_drawable_offset_check_box);
//        startDrawableOffsetCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                itemDecorationVisibilityChangeListener.onStartDrawableOffsetVisibilityChange();
//            }
//        });

        endOffsetCheckBox = (CheckBox) findViewById(R.id.end_offset_check_box);
        endOffsetCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                itemDecorationVisibilityChangeListener.onEndOffsetVisibilityChange();
            }
        });

        layoutManagerSpinner = (Spinner) findViewById(R.id.layout_manager_spinner);
        ArrayAdapter<CharSequence> layoutManagerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.layout_managers, android.R.layout.simple_spinner_dropdown_item);
        layoutManagerSpinner.setAdapter(layoutManagerAdapter);
        layoutManagerSpinner.setOnItemSelectedListener(layoutManagerSpinnerListener);

//        endDrawableOffsetCheckBox = (CheckBox) findViewById(R.id.end_drawable_offset_check_box);
//        endDrawableOffsetCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                itemDecorationVisibilityChangeListener.onEndDrawableOffsetVisibilityChange();
//            }
//        });

        orientationSpinner = (Spinner) findViewById(R.id.orientation_spinner);
        ArrayAdapter<CharSequence> orientationAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.orientations, android.R.layout.simple_spinner_dropdown_item);
        orientationSpinner.setAdapter(orientationAdapter);
        orientationSpinner.setOnItemSelectedListener(orientationSpinnerListener);

        offsetTypeSpinner = (Spinner) findViewById(R.id.offset_type_spinner);
        ArrayAdapter<CharSequence> offsetTypeAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.offset_types, android.R.layout.simple_spinner_dropdown_item);
        offsetTypeSpinner.setAdapter(offsetTypeAdapter);
        offsetTypeSpinner.setOnItemSelectedListener(offsetTypeSpinnerListener);
    }

    public void setItemDecorationVisibilityChangeListener(ItemDecorationVisibilityChangeListener itemDecorationVisibilityChangeListener) {
        this.itemDecorationVisibilityChangeListener = itemDecorationVisibilityChangeListener;
    }

    public void setLayoutManagerChangeListener(LayoutManagerChangeListener layoutManagerChangeListener) {
        this.layoutManagerChangeListener = layoutManagerChangeListener;
    }

    public void setOrientationChangeListener(OrientationChangeListener orientationChangeListener) {
        this.orientationChangeListener = orientationChangeListener;
    }

    public void setOffsetTypeChangeListener(OffsetTypeChangeListener offsetTypeChangeListener) {
        this.offsetTypeChangeListener = offsetTypeChangeListener;
    }

    private AdapterView.OnItemSelectedListener layoutManagerSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case POSITION_LINEAR_LAYOUT_MANAGER:
                    layoutManagerChangeListener.onLinearLayoutManagerSelected();
                    break;
                case POSITION_GRID_LAYOUT_MANAGER:
                    layoutManagerChangeListener.onGridLayoutManagerSelected();
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // Do nothing
        }
    };

    private AdapterView.OnItemSelectedListener orientationSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case POSITION_HORIZONTAL:
                    orientationChangeListener.onHorizontalSelected();
                    break;
                case POSITION_VERTICAL:
                    orientationChangeListener.onVerticalSelected();
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // Do nothing
        }
    };

    private AdapterView.OnItemSelectedListener offsetTypeSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case POSITION_DRAWABLE:
                    offsetTypeChangeListener.onDrawableSelected();
                    break;
                case POSITION_PIXEL:
                    offsetTypeChangeListener.onPixelSelected();
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // Do nothing
        }
    };
}
