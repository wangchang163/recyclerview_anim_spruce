package com.example.recyclerview_spruce_anim;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.willowtreeapps.spruce.Spruce;
import com.willowtreeapps.spruce.animation.DefaultAnimations;
import com.willowtreeapps.spruce.sort.ContinuousSort;
import com.willowtreeapps.spruce.sort.ContinuousWeightedSort;
import com.willowtreeapps.spruce.sort.CorneredSort;
import com.willowtreeapps.spruce.sort.DefaultSort;
import com.willowtreeapps.spruce.sort.InlineSort;
import com.willowtreeapps.spruce.sort.LinearSort;
import com.willowtreeapps.spruce.sort.RadialSort;
import com.willowtreeapps.spruce.sort.RandomSort;
import com.willowtreeapps.spruce.sort.SortFunction;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerview;
    private SlimAdapter slimAdapter;
    private SwipeRefreshLayout rootview;
    private Animator spruceAnimator;
    private SortFunction sortFunction;
    private CorneredSort.Corner corner;
    private Animator[] animators;
    private int flag = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerview.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(this, 4) {
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                super.onLayoutChildren(recycler, state);
                initSpruce();
            }
        };
        mRecyclerview.setLayoutManager(manager);
        slimAdapter = SlimAdapter.create().register(R.layout.item_image, new SlimInjector<User>() {

            @Override
            public void onInject(User data, IViewInjector injector) {

                injector.clicked(R.id.item, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(MainActivity.this, SecondActivity.class));
                    }
                });

            }
        }).attachTo(mRecyclerview);
        slimAdapter.updateData(getData());
        initAnimator();
        rootview = (SwipeRefreshLayout) findViewById(R.id.rootview);
        rootview.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                resetChildViewsAndStartSort();
                rootview.setRefreshing(false);
            }
        });
    }

    private void initAnimator() {
        animators = new Animator[]{
                DefaultAnimations.shrinkAnimator(mRecyclerview, /*duration=*/800),
                DefaultAnimations.fadeInAnimator(mRecyclerview, /*duration=*/800)
        };
    }

    private void initSpruce() {
        switch (flag) {
            case 1:
                sortFunction = new DefaultSort(100);
                break;
            case 2:
                corner = CorneredSort.Corner.TOP_LEFT;
                sortFunction = new CorneredSort(100, false, corner);
                break;
            case 3:
                sortFunction = new ContinuousSort(100, false, RadialSort.Position.TOP_LEFT);
                break;
            case 4:
                sortFunction = new ContinuousWeightedSort(100, false, RadialSort.Position.TOP_LEFT, ContinuousWeightedSort.LIGHT_WEIGHT, ContinuousWeightedSort.LIGHT_WEIGHT);
                break;
            case 5:
                sortFunction = new InlineSort(100, false, corner);
                break;
            case 6:
                sortFunction = new LinearSort(100, false, LinearSort.Direction.BOTTOM_TO_TOP);
                break;
            case 7:
                sortFunction = new RadialSort(100, false, RadialSort.Position.TOP_LEFT);
                break;
            case 8:
                sortFunction = new RandomSort(100);
                break;
            case 9:
                break;
            default:
                sortFunction = new DefaultSort(100);
                break;

        }
        spruceAnimator = new Spruce.SpruceBuilder(mRecyclerview)
                .sortWith(sortFunction)
                .animateWith(animators)
                .start();
    }

    private List<User> getData() {
        List<User> uList = new ArrayList<>();
        for (int i = 0; i < 64; i++) {
            uList.add(new User());
        }
        return uList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (spruceAnimator != null) {
            spruceAnimator.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (spruceAnimator != null) {
            spruceAnimator.cancel();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.default_sort:
                flag = 1;
                initSpruce();
                break;
            case R.id.cornered_sort:
                flag = 2;
                initSpruce();
                break;
            case R.id.continuous_sort:
                flag = 3;
                initSpruce();
                break;
            case R.id.continuous_weighted_sort:
                flag = 4;
                initSpruce();
                break;
            case R.id.inline_sort:
                flag = 5;
                initSpruce();
                break;
            case R.id.linear_sort:
                flag = 6;
                initSpruce();
                break;
            case R.id.radial_sort:
                flag = 7;
                initSpruce();
                break;
            case R.id.random_sort:
                flag = 8;
                initSpruce();
                break;
            case R.id.snake_sort:
                flag = 9;
                initSpruce();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void resetChildViewsAndStartSort() {
        if (spruceAnimator != null) {
            spruceAnimator.cancel();
        }
        initSpruce();
    }
}
