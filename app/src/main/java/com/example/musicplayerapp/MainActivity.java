package com.example.musicplayerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicplayerapp.utill.GlideUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Map;

//Login Page
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private EditText user, psw;
    private String user_str, psw_str;
    private TextView btn_login, btn_sgin, btn_skip;
    SharedPreferences sp;
    SQLiteDatabase db;
    MyOpenHelper Helper;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        user = (EditText) findViewById(R.id.et_name);
        psw = (EditText) findViewById(R.id.et_password);
        btn_login = (TextView) findViewById(R.id.btn_login);
        btn_sgin = (TextView) findViewById(R.id.btn_sgin);
        btn_skip = (TextView) findViewById(R.id.skip_button);

        btn_login.setOnClickListener(this);
        btn_sgin.setOnClickListener(this);
        btn_skip.setOnClickListener(this);

        Helper = new MyOpenHelper(this);
        db = Helper.getReadableDatabase(); //Database mySQLite
        sp = this.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        verifyStoragePermissions(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                user_str = user.getText().toString();
                psw_str = psw.getText().toString();
                if (TextUtils.isEmpty(user_str) || TextUtils.isEmpty(psw_str)) {
                    Toast.makeText(this, "Account or password cannot be blank", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Cursor cursor = db.query("formation", new String[]{"password"}, "name=?", new String[]{user_str}, null, null, null);
                    if (cursor.moveToNext()) {
                        String psw_query = cursor.getString(cursor.getColumnIndex("password"));
                        if (psw_str.equals(psw_query)) {
                            startActivity(new Intent(this, ThemActivity.class));
                            finish();
                        } else {
                            Toast.makeText(this, "Wrong account or password", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } else {
                        Toast.makeText(this, "Account does not exist, please register first!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                break;
            case R.id.btn_sgin:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.skip_button:
                Intent intent_skip = new Intent(this, SelectPage.class);
                startActivity(intent_skip);
                break;

        }
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

}