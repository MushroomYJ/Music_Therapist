package com.example.musicplayerapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


public class StepActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private boolean running;
    private float totalSteps;
    private float previousTotalSteps;
    final Handler handler = new Handler();
    final int delay = 15000;

    @TargetApi(Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_activity);

        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }

        loadData();
        resetSteps();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    protected void onResume() {
        super.onResume();
        running = true;

        Sensor stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (stepSensor == null) {
            Toast.makeText(this, "No sensor detected on this device", Toast.LENGTH_SHORT).show();
        }
        else {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI);
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        TextView tv_stepsTaken = findViewById(R.id.tv_stepsTaken);
        if (running) {
            this.totalSteps = event.values[0];
            int currentSteps = (int)this.totalSteps - (int)this.previousTotalSteps;
            tv_stepsTaken.setText(String.valueOf(currentSteps));
        }
    }

    public final void resetSteps() {

        TextView tv_stepsTaken = findViewById(R.id.tv_stepsTaken);
        tv_stepsTaken.setOnClickListener((new OnClickListener() {
            public void onClick(View it) {
                Toast.makeText(StepActivity.this, "Look at the user manual below", Toast.LENGTH_SHORT).show();
            }
        }));

        handler.postDelayed(new Runnable() {
            public void run() {
                int currentSteps = (int)totalSteps - (int)previousTotalSteps;
                playWithStep(currentSteps);
                previousTotalSteps = totalSteps;
                tv_stepsTaken.setText(String.valueOf(0));
                saveData();
                System.out.println("myHandler: here!");
                handler.postDelayed(this, delay);
            }
        }, delay);

    }

    private String currentAssetsFolderName;
    private void playWithStep(int step){
        String assetsFolderName;
        if (step >=0 && step <= 7){
            assetsFolderName = "music/zero";
        }else if (step >7 && step <=16){
            assetsFolderName ="music/mid";
        }else {
            assetsFolderName = "music/fast";
        }
        if (assetsFolderName.equals(currentAssetsFolderName)){
            return;
        }
        currentAssetsFolderName = assetsFolderName;
        Intent intent = new Intent(this,MusicDetailActivity.class);
        intent.putExtra("playWithStep",true);
        intent.putExtra("assetsFolderName",assetsFolderName);
        startActivity(intent);
    }

    private final void saveData() {
        SharedPreferences sharedPreferences1 = this.getSharedPreferences("myPrefs", 0);
        Editor editor = sharedPreferences1.edit();
        editor.putFloat("key1", this.previousTotalSteps);
        editor.apply();
    }

    private final void loadData() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("myPrefs", 0);
        float savedNumber = sharedPreferences.getFloat("key1", 0.0F);
        Log.d("MainActivity", String.valueOf(savedNumber));
        this.previousTotalSteps = savedNumber;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

}