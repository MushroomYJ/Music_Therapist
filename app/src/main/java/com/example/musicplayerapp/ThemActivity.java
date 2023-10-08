package com.example.musicplayerapp;

import android.content.Intent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class ThemActivity extends BaseActivity implements View.OnClickListener {

    private int them;
    private boolean zhut;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_them;
    }

    @Override
    protected void initView() {
        zhut = getIntent().getBooleanExtra("zhut", false);
        findViewById(R.id.btn_confirm).setOnClickListener(this);
        findViewById(R.id.cb_default).setOnClickListener(this);
        findViewById(R.id.cb_black).setOnClickListener(this);
        findViewById(R.id.cb_red).setOnClickListener(this);
        findViewById(R.id.cb_yellow).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cb_default:
                them = 5;
                break;
            case R.id.cb_black:
                them = 2;
                break;
            case R.id.cb_red:
                them = 3;
                break;
            case R.id.cb_yellow:
                them = 4;
                break;
            case R.id.btn_confirm:
                AppPreferences.put(this, "them", them);
                if (zhut) {
                    Intent intent = new Intent(this, SelectPage.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(this, BgActivity.class));
                }
                finish();
                break;

        }
    }
}
