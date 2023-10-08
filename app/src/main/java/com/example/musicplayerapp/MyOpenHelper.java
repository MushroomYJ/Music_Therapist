package com.example.musicplayerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//sqlite database
public class MyOpenHelper extends SQLiteOpenHelper {

    public MyOpenHelper(Context context){
        super(context,"itcast.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE formation(_id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(20),password VARCHAR(20),sex VARCHAR(20),address VARCHAR(20))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addData(SQLiteDatabase db, String name_str, String psw_str) {
        ContentValues values = new ContentValues();
        values.put("name",name_str);
        values.put("password",psw_str);
        db.insert("formation",null,values);
    }
}
