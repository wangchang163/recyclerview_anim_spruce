package com.example.recyclerview_spruce_anim;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.willowtreeapps.spruce.Spruce;
import com.willowtreeapps.spruce.animation.DefaultAnimations;
import com.willowtreeapps.spruce.sort.DefaultSort;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SlimAdapter slimAdapter;
    private Animator spruceAnimator;
    private int flag = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this) {
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                super.onLayoutChildren(recycler, state);
                initSpruce();
            }
        };
        recyclerView.setLayoutManager(manager);
        slimAdapter = SlimAdapter.create().register(R.layout.item_image, new SlimInjector<User>() {

            @Override
            public void onInject(User data, IViewInjector injector) {

            }
        }).attachTo(recyclerView);
        slimAdapter.updateData(getData());
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                resetChildViewsAndStartSort();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void resetChildViewsAndStartSort() {
        if (spruceAnimator != null) {
            spruceAnimator.cancel();
        }
        initSpruce();
    }


    private void initSpruce() {
        switch (flag) {
            case 1:
                spruceAnimator = new Spruce.SpruceBuilder(recyclerView)
                        .sortWith(new DefaultSort(100))
                        .animateWith(DefaultAnimations.shrinkAnimator(recyclerView, 800),
                                ObjectAnimator.ofFloat(recyclerView, "alpha", 0f, 1f).setDuration(800))
                        .start();
                break;
            case 2:
                spruceAnimator = new Spruce.SpruceBuilder(recyclerView)
                        .sortWith(new DefaultSort(100))
                        .animateWith(DefaultAnimations.shrinkAnimator(recyclerView, 800),
                                ObjectAnimator.ofFloat(recyclerView, "rotation", 0f, 360f).setDuration(800))
                        .start();
                break;
            case 3:
                spruceAnimator = new Spruce.SpruceBuilder(recyclerView)
                        .sortWith(new DefaultSort(100))
                        .animateWith(DefaultAnimations.shrinkAnimator(recyclerView, 800),
                                ObjectAnimator.ofFloat(recyclerView, "rotationX", 0f, 180f, 0f).setDuration(800))
                        .start();
                break;
            case 4:
                spruceAnimator = new Spruce.SpruceBuilder(recyclerView)
                        .sortWith(new DefaultSort(100))
                        .animateWith(DefaultAnimations.shrinkAnimator(recyclerView, 800),
                                ObjectAnimator.ofFloat(recyclerView, "rotationY", 0f, 180f, 0f).setDuration(800))
                        .start();
                break;
            case 5:
                spruceAnimator = new Spruce.SpruceBuilder(recyclerView)
                        .sortWith(new DefaultSort(100))
                        .animateWith(DefaultAnimations.shrinkAnimator(recyclerView, 800),
                                ObjectAnimator.ofFloat(recyclerView, "translationX", -recyclerView.getWidth(), 0f).setDuration(800))
                        .start();
                break;
            case 6:
                spruceAnimator = new Spruce.SpruceBuilder(recyclerView)
                        .sortWith(new DefaultSort(100))
                        .animateWith(DefaultAnimations.shrinkAnimator(recyclerView, 800),
                                ObjectAnimator.ofFloat(recyclerView, "translationY", recyclerView.getWidth(), 0f).setDuration(800))
                        .start();
                break;
            case 7:
                spruceAnimator = new Spruce.SpruceBuilder(recyclerView)
                        .sortWith(new DefaultSort(100))
                        .animateWith(DefaultAnimations.shrinkAnimator(recyclerView, 800),
                                ObjectAnimator.ofFloat(recyclerView, "scaleX", 1f, 3f, 1f).setDuration(800))
                        .start();
                break;
            case 8:
                spruceAnimator = new Spruce.SpruceBuilder(recyclerView)
                        .sortWith(new DefaultSort(100))
                        .animateWith(DefaultAnimations.shrinkAnimator(recyclerView, 800),
                                ObjectAnimator.ofFloat(recyclerView, "scaleY", 1f, 3f, 1f).setDuration(800))
                        .start();
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_anim, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.alpha:
                flag = 1;
                initSpruce();
                break;
            case R.id.rotation:
                flag = 2;
                initSpruce();
                break;
            case R.id.rotationX:
                flag = 3;
                initSpruce();
                break;
            case R.id.rotationY:
                flag = 4;
                initSpruce();
                break;
            case R.id.translationX:
                flag = 5;
                initSpruce();
                break;
            case R.id.translationY:
                flag = 6;
                initSpruce();
                break;
            case R.id.scaleX:
                flag = 7;
                initSpruce();
                break;
            case R.id.scaleY:
                flag = 8;
                initSpruce();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<User> getData() {
        List<User> uList = new ArrayList<>();
        for (int i = 0; i < 64; i++) {
            uList.add(new User());
        }
        return uList;
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
}
