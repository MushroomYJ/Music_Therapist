package com.example.musicplayerapp;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

//Make next steps easier, helper file
public class BaseActivity extends AppCompatActivity {

    public ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeTheme((Integer) AppPreferences.get(this, "them", 5));
        setContentView(getLayoutResourceId());
        actionBar = getSupportActionBar();
        initView();
    }

    protected int getLayoutResourceId() {
        return -1;
    }

    protected void initView() {
    }

    public void changeTheme(int them) {
        switch (them) {
            case 1:
            case 5:
                setTheme(R.style.AppTheme);
                break;
            case 2:
                setTheme(R.style.BlackTheme);
                break;
            case 3:
                setTheme(R.style.RedTheme);
                break;
            case 4:
                setTheme(R.style.YellowTheme);
                break;
        }
    }

    public void setBackGround(String img, View view) {

    }
}
