package com.example.musicplayerapp;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

//Registration page, user's information will be on SQLite database
//注册页面 注册信息存入sqlite数据库
public class RegisterActivity extends BaseActivity {

    private EditText mName, mPassword, mPassword1;
    private String name_str, psw_str, psw_str1;
    MyOpenHelper Helper;
    SQLiteDatabase db;


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        super.initView();
        Helper = new MyOpenHelper(this);
        db = Helper.getWritableDatabase();

        mName = (EditText) findViewById(R.id.et_name);
        mPassword = (EditText) findViewById(R.id.et_password);
        mPassword1 = (EditText) findViewById(R.id.re_et_password1);
    }

    public void click_register(View v) {
        name_str = mName.getText().toString();
        psw_str = mPassword.getText().toString();
        psw_str1 = mPassword1.getText().toString();

        if (TextUtils.isEmpty(name_str)) {
            Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(psw_str)) {
            Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!psw_str.equals(psw_str1)) {
            Toast.makeText(this, "Two passwords must match", Toast.LENGTH_SHORT).show();
            return;
        }

        Helper.addData(db, name_str, psw_str);
        Toast.makeText(RegisterActivity.this, name_str + " Signed up Success", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
