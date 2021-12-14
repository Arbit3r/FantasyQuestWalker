package com.example.fantasyquestwalker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor sensor;
    private double MagnitudePrevious;
    float stepCount = 0;

    private float savedStepCount;
    private float createStepSave;

    Animation whiteAnimEnabled, whiteAnimDisabled, textAnimEnabled, textAnimDisabled, textAnimLoad;
    LinearLayout white, text;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.textbox);
        white = findViewById(R.id.white);
        white.setVisibility(View.GONE);

        new AnimationUtils();
        whiteAnimEnabled = AnimationUtils.loadAnimation(this, R.anim.white_animation_enabled);
        whiteAnimDisabled = AnimationUtils.loadAnimation(this, R.anim.white_animation_disabled);
        textAnimEnabled = AnimationUtils.loadAnimation(this, R.anim.text_animation_enabled);
        textAnimDisabled = AnimationUtils.loadAnimation(this, R.anim.text_animation_disabled);
        textAnimLoad = AnimationUtils.loadAnimation(this, R.anim.text_animation_load);

        text.startAnimation(textAnimLoad);

        ToggleButton toggle = findViewById(R.id.togglebutton);
        toggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                white.setVisibility(View.VISIBLE);
                white.startAnimation(whiteAnimEnabled);
                text.startAnimation(textAnimEnabled);
            } else {
                white.setVisibility(View.GONE);
                white.startAnimation(whiteAnimDisabled);
                text.startAnimation(textAnimDisabled);
            }
        });
        Button btn = (Button) findViewById(R.id.changejourney);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, JourneySelection.class));
            }
        });


        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.jalka);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 101);
        } else {
            Log.d("sensor.permission", "denied");
        }

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        stepCount = event.values[0];
        TextView tv = findViewById(R.id.jalka);
        tv.setText(Float.toString(stepCount));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, sensorManager.SENSOR_DELAY_NORMAL);

        SharedPreferences prefGet = getSharedPreferences("StepsPref", Activity.MODE_PRIVATE);
        stepCount = prefGet.getFloat("StepKey", 0);

        TextView tv = findViewById(R.id.jalka);
        tv.setText(Float.toString(stepCount));
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this, sensor);


        createStepSave = stepCount;
        SharedPreferences prefPut = getSharedPreferences("StepsPref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();
        prefEditor.putFloat("StepKey", createStepSave);
        prefEditor.commit();
    }


}