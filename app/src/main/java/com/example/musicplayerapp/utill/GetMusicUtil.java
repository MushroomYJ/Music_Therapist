package com.example.musicplayerapp.utill;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.musicplayerapp.Music;

import java.util.ArrayList;

//获取手机中音乐的工具类
public class GetMusicUtil {

    public ArrayList<Music> getMusic(ContentResolver resolver) {
        ArrayList<Music> mlist = new ArrayList<>();
        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);

        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                Music music = new Music();
                long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                int ismusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));

                if (ismusic != 0) {
                    music.setId(id);
                    music.setTitle(title);
                    music.setArtist(artist);
                    music.setAlbum(album);
                    music.setUrl(url);
                    music.setDuration(duration);
                    music.setSize(size);

                    mlist.add(music);
                }
                cursor.moveToNext();
            }
        }

        mlist.add(new Music("The Way I Still Love You - Reynard Silva", "https://lvdiimg.dbpdq.cn/Reynard%20Silva%20-%20The%20Way%20I%20Still%20Love%20You.mp3"));
        mlist.add(new Music("Dior - Pop Smoke", "https://lvdiimg.dbpdq.cn/a.mp3"));
        mlist.add(new Music("Don't Start Now - Duo Lipa", "https://lvdiimg.dbpdq.cn/b.mp3"));
        mlist.add(new Music("Drop the world - Eminem, Lil Wayne", "https://lvdiimg.dbpdq.cn/c.mp3"));
        mlist.add(new Music("Faded - Alan Walker", "https://lvdiimg.dbpdq.cn/d.mp3"));

        return mlist;
    }
}
