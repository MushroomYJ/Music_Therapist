package com.example.musicplayerapp;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.musicplayerapp.adapter.AppBgAdapter;
import com.example.musicplayerapp.adapter.BaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class BgActivity extends BaseActivity implements BaseRecyclerAdapter.onItemClickListener {

    private TextView btn_confirm;
    private RecyclerView mBgRecycler;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;

    private List<Integer> mBgList;
    private AppBgAdapter mAppBgAdapter;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.app_background;
    }

    @Override
    protected void initView() {
        mBgRecycler = findViewById(R.id.bg_recycler);
        btn_confirm = findViewById(R.id.btn_confirm);

        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mBgRecycler.setLayoutManager(mStaggeredGridLayoutManager);
        mBgList = new ArrayList<>();
        mBgList.add(R.drawable.a);
        mBgList.add(R.drawable.b);
        mBgList.add(R.drawable.c);
        mBgList.add(R.drawable.d);
        mBgList.add(R.drawable.e);
        mBgList.add(R.drawable.f);
        mBgList.add(R.drawable.g);
        mBgList.add(R.drawable.h);
        mBgList.add(R.drawable.i);
        mBgList.add(R.drawable.j);
        mBgList.add(R.drawable.k);
        mBgList.add(R.drawable.l);
        mBgList.add(R.drawable.m);
        mBgList.add(R.drawable.n);
        mBgList.add(R.drawable.o);
        mBgList.add(R.drawable.p);

        mAppBgAdapter = new AppBgAdapter(this);
        mAppBgAdapter.replaceAllItems(mBgList);
        mBgRecycler.setAdapter(mAppBgAdapter);
        mAppBgAdapter.setOnItemClickListener(this);
        btn_confirm.setOnClickListener(v -> startActivity(new Intent(this, SelectPage.class)));
    }

    @Override
    public void onItemClick(BaseRecyclerAdapter adapter, View view, int position) {
        Integer bg = (Integer) adapter.getItem(position);
        AppPreferences.put(this, "AppBg", bg);
        Intent intent = new Intent(this, SelectPage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
