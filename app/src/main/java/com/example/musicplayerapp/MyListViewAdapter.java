package com.example.musicplayerapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

//Music list adapter
public class MyListViewAdapter extends BaseAdapter {
    ArrayList<Music> musicList;
    Context context;
    ArrayList<Music> shouCangList;
    SharedPreferences sp;
    Boolean isSearch;

    public MyListViewAdapter(Context context, boolean isSearch) {
        this.context = context;
        this.isSearch = isSearch;
        sp = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        shouCangList = new Gson().fromJson(sp.getString("shou_cang_music", ""), new TypeToken<List<Music>>() {
        }.getType());
        if (shouCangList == null) {
            shouCangList = new ArrayList<>();
        }
    }

    @Override
    public int getCount() {
        return musicList != null ? musicList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler hodler;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_listview, null);
            hodler = new ViewHodler();
//            hodler.iv = convertView.findViewById(R.id.iv);
            hodler.tvMusicTitle = convertView.findViewById(R.id.tv_music_title);
            hodler.tvMusicSinger = convertView.findViewById(R.id.tv_music_singer);
            hodler.btn_delete = convertView.findViewById(R.id.btn_delete);
            if (isSearch) {
                hodler.btn_delete.setVisibility(View.GONE);
            }
            convertView.setTag(hodler);
        } else {
            hodler = (ViewHodler) convertView.getTag();
        }
        Music music = musicList.get(position);
        hodler.tvMusicTitle.setText(music.getTitle());
        hodler.btn_delete.setOnClickListener(v -> {
            musicList.remove(music);
            notifyDataSetChanged();
        });
        return convertView;
    }

    public void replaceAllItems(List<Music> itemList) {
        if (musicList == null) {
            musicList = new ArrayList<>();
        } else {
            musicList.clear();
        }
        if (itemList != null) {
            musicList.addAll(itemList);
        }
        notifyDataSetChanged();
    }

    public ArrayList<Music> getMusicList() {
        return musicList;
    }

    class ViewHodler {
        ImageView iv;
        TextView tvMusicTitle, tvMusicSinger;
        TextView btn_delete;
    }
}
