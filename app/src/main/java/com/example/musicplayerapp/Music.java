package com.example.musicplayerapp;

import android.os.Parcel;
import android.os.Parcelable;

//Music, names, local folder
//音乐实体类 （存放音乐标题、路径等）
public class Music implements Parcelable {
    private long id;
    private String title;
    private String artist;
    private long duration;
    private long size;
    private String album;
    private String url;

    public Music(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.artist);
        dest.writeLong(this.duration);
        dest.writeLong(this.size);
        dest.writeString(this.album);
        dest.writeString(this.url);
    }

    public Music() {
    }

    protected Music(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.artist = in.readString();
        this.duration = in.readLong();
        this.size = in.readLong();
        this.album = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<Music> CREATOR = new Parcelable.Creator<Music>() {
        @Override
        public Music createFromParcel(Parcel source) {
            return new Music(source);
        }

        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };
}
