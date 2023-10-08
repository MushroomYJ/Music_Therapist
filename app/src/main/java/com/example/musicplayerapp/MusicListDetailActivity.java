package com.example.musicplayerapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicplayerapp.utill.GetMusicUtil;
import com.example.musicplayerapp.utill.GlideUtils;

//Music list page
public class MusicListDetailActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private ListView listView;
    MyListViewAdapter adapter;
    ArrayList<Music> mList;
//    ArrayList<Music> mSongList;
    ArrayList<Music> mSosuoList;
    LinearLayout sosuo_layout;
    ImageView bg_img;
    EditText et_sosuo;
    TextView tv_sosuo;
    Boolean isSearch;
    ImageView mc_cover;


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_music_list_detail;
    }

    @Override
    protected void initView() {
        bg_img = findViewById(R.id.bg_img);
        sosuo_layout = findViewById(R.id.sosuo_layout);
        listView = findViewById(R.id.iv_music);
        et_sosuo = findViewById(R.id.et_sosuo);
        tv_sosuo = findViewById(R.id.tv_sosuo);

        GlideUtils.loadBlurImage(getApplicationContext(), (Integer) AppPreferences.get(getApplicationContext(), "AppBg", R.drawable.default_bg), bg_img);
        mSosuoList = new ArrayList<>();
        final Intent intent = getIntent();
        mList = (ArrayList<Music>) intent.getSerializableExtra("music");
//        mSongList = GetMusicUtil.getSong(getContentResolver());



        isSearch = intent.getBooleanExtra("search", false);
        if (isSearch) {
            sosuo_layout.setVisibility(View.VISIBLE);
        } else {
            sosuo_layout.setVisibility(View.GONE);
        }
        adapter = new MyListViewAdapter(this, isSearch);
        adapter.replaceAllItems(mList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        tv_sosuo.setOnClickListener(this);
        et_sosuo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    adapter.replaceAllItems(mList);
                }
            }
        }

        );
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, MusicDetailActivity.class);
        intent.putParcelableArrayListExtra("musicBean", adapter.getMusicList());
        intent.putExtra("position", position);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(et_sosuo.getText().toString())) {
            Toast.makeText(this, "Please enter the song name", Toast.LENGTH_SHORT).show();
            return;
        }
        mSosuoList.clear();
//        for (Music music : mSongList) {
//            if (music.getTitle().contains(et_sosuo.getText().toString())) {
//                mSosuoList.add(music);
//            }
//        }
        adapter.replaceAllItems(mSosuoList);
    }
}
