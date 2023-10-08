package com.example.musicplayerapp;

import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayerapp.adapter.BaseRecyclerAdapter;
//import com.example.musicplayerapp.bean.MusicBean;
import com.example.musicplayerapp.utill.GetMusicUtil;
import com.example.musicplayerapp.utill.GlideUtils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class SelectPage extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, BaseRecyclerAdapter.onItemClickListener {

    private DrawerLayout mDrawerLayout;
    private ImageView iv_bg;
    private NavigationView mNavView;
    private RecyclerView mMusicRecycler;
    private ArrayList<Music> musicList;
    private ImageView maim_StartBtn;
    //    private MusicAlbumAdapter mAlbumAdapter;
//    private ArrayList<MusicBean> mMusicAlbumList;
    private boolean isDrawerOpen;
    private int i = 1;
    private boolean mBackKeyPressed;


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_select_page;
    }

    @Override
    protected void initView() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        iv_bg = findViewById(R.id.iv_bg);
        mNavView = findViewById(R.id.nav_view);
        mMusicRecycler = findViewById(R.id.music_recycler);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.icon_geng);


//        mMusicAlbumList = new ArrayList<>();
        musicList = new GetMusicUtil().getMusic(getContentResolver());
        GlideUtils.loadBlurImage(getApplicationContext(), (Integer) AppPreferences.get(getApplicationContext(), "AppBg", R.drawable.default_bg), iv_bg);
        mNavView.setNavigationItemSelectedListener(this);
        mMusicRecycler.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
//        mAlbumAdapter = new MusicAlbumAdapter(this);
//        mMusicRecycler.setAdapter(mAlbumAdapter);
//        mAlbumAdapter.setOnItemClickListener(this);

        //Monitor
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {
                Log.i("---", "Swiping");
            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
                Log.i("---", "Open");
                isDrawerOpen = true;
            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
                Log.i("---", "Close");
                isDrawerOpen = false;
            }

            @Override
            public void onDrawerStateChanged(int i) {
                Log.i("---", "Status Changes");
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (isDrawerOpen) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            } else {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, MusicListDetailActivity.class);
        switch (item.getItemId()) {
            case R.id.item_local_songs:
                intent.putExtra("music", musicList);
                startActivity(intent);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.item_step_count:
                Intent intent4 = new Intent(this, StepActivity.class);
                intent4.putExtra("step", true);
                startActivity(intent4);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.item_change_theme:
                Intent intent1 = new Intent(this, ThemActivity.class);
                intent1.putExtra("zhut", true);
                startActivity(intent1);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.item_change_background:
                startActivity(new Intent(this, BgActivity.class));
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.item_logout:
                startActivity(new Intent(this, MainActivity.class));
                mDrawerLayout.closeDrawer(GravityCompat.START);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onItemClick(BaseRecyclerAdapter adapter, View view, int position) {
        Intent intent = new Intent(this, MusicListDetailActivity.class);
        intent.putExtra("search", true);
        intent.putExtra("music", musicList);
        startActivity(intent);
    }

}
