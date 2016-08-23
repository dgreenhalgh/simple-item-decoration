package com.dgreenhalgh.android.simpleitemdecorationsample.two;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dgreenhalgh.android.simpleitemdecoration.DividerItemDecoration;
import com.dgreenhalgh.android.simpleitemdecoration.StartOffsetItemDecoration;
import com.dgreenhalgh.android.simpleitemdecoration.linear.EndOffsetItemDecoration;
import com.dgreenhalgh.android.simpleitemdecorationsample.R;
import com.dgreenhalgh.android.simpleitemdecorationsample.view.ControlView;

import java.util.List;

public class ContentActivity extends AppCompatActivity {

    private static final int SPAN_COUNT = 3;

    private ControlView controlView;
    private RecyclerView recyclerView;

    private Drawable dividerDrawable;
    private int offsetPx;

    private RecyclerView.ItemDecoration dividerItemDecoration;
    private RecyclerView.ItemDecoration startOffsetItemDecoration;
    private RecyclerView.ItemDecoration endOffsetItemDecoration;

    private boolean dividersVisible;
    private boolean startOffsetVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        controlView = (ControlView) findViewById(R.id.control_view);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        initListeners();
        initData();
        initDividers();
    }

    private void initListeners() {
        controlView.setItemDecorationVisibilityChangeListener(itemDecorationVisibilityChangeListener);
        controlView.setLayoutManagerChangeListener(layoutManagerChangeListener);
        controlView.setOrientationChangeListener(orientationChangeListener);
        controlView.setOffsetTypeChangeListener(offsetTypeChangeListener);
    }

    private void initData() {
        List<String> sampleStringList = SampleDataBank.getSampleData();
        recyclerView.setAdapter(new SimpleItemDecorationSampleListAdapter(sampleStringList));
    }

    private void initDividers() {
        dividerDrawable = ContextCompat.getDrawable(ContentActivity.this, R.drawable.divider_sample);

        Resources resources = getResources();
        offsetPx = resources.getDimensionPixelOffset(R.dimen.divider_size);
    }

    private ControlView.ItemDecorationVisibilityChangeListener itemDecorationVisibilityChangeListener = new ControlView.ItemDecorationVisibilityChangeListener() {
        @Override
        public void onDividerVisibilityChange() {
            if (dividersVisible) { // TODO: Pull selected and deselected into ControlView
                recyclerView.removeItemDecoration(dividerItemDecoration);
                dividersVisible = false;
            } else {
                recyclerView.addItemDecoration(dividerItemDecoration);
                dividersVisible = true;
            }
        }

        @Override
        public void onStartOffsetVisibilityChange() {
            if (startOffsetVisible) {
                recyclerView.removeItemDecoration(startOffsetItemDecoration);
                startOffsetVisible = false;
            } else {
                recyclerView.addItemDecoration(startOffsetItemDecoration);
                startOffsetVisible = true;
            }
        }

//        @Override
//        public void onStartDrawableOffsetVisibilityChange() {
//            recyclerView.setLayoutManager(new GridLayoutManager(ContentActivity.this, 3));
//        }

        @Override
        public void onEndOffsetVisibilityChange() {

        }

//        @Override
//        public void onEndDrawableOffsetVisibilityChange() {
//
//        }
    };

    private ControlView.LayoutManagerChangeListener layoutManagerChangeListener = new ControlView.LayoutManagerChangeListener() {
        @Override
        public void onLinearLayoutManagerSelected() {
            recyclerView.setLayoutManager(new LinearLayoutManager(ContentActivity.this));
            dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
            recyclerView.invalidateItemDecorations();
        }

        @Override
        public void onGridLayoutManagerSelected() {
            recyclerView.setLayoutManager(new GridLayoutManager(ContentActivity.this, SPAN_COUNT));
            dividerItemDecoration = new DividerItemDecoration(dividerDrawable, dividerDrawable);
            recyclerView.invalidateItemDecorations();
        }
    };

    private ControlView.OrientationChangeListener orientationChangeListener = new ControlView.OrientationChangeListener() { // todo is vert by defualt despite this order
        @Override
        public void onHorizontalSelected() {
            ((LinearLayoutManager) recyclerView.getLayoutManager()).setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.invalidateItemDecorations();
        }

        @Override
        public void onVerticalSelected() {
            ((LinearLayoutManager) recyclerView.getLayoutManager()).setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.invalidateItemDecorations();
        }
    };

    private ControlView.OffsetTypeChangeListener offsetTypeChangeListener = new ControlView.OffsetTypeChangeListener() {
        @Override
        public void onDrawableSelected() {
            dividerItemDecoration = new DividerItemDecoration(dividerDrawable); // TODO: Grid? maybe make the constructor handle both horiz and vert
            startOffsetItemDecoration = new StartOffsetItemDecoration(dividerDrawable);
            endOffsetItemDecoration = new EndOffsetItemDecoration(dividerDrawable);
            recyclerView.invalidateItemDecorations(); // TODO: Figure out this reset. It's more than an invalidation.
        }

        @Override
        public void onPixelSelected() {
            dividerItemDecoration = new DividerItemDecoration(offsetPx);
            startOffsetItemDecoration = new StartOffsetItemDecoration(offsetPx);
            endOffsetItemDecoration = new EndOffsetItemDecoration(offsetPx);
            recyclerView.invalidateItemDecorations(); // TODO: Figure out this reset. It's more than an invalidation.
        }
    }; // TODO: Figure out pixel vs drawable vs layoutmanager
}
