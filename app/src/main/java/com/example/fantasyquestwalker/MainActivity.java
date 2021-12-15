package com.example.fantasyquestwalker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor sensor;
    float stepCount = 0;
    private int savedJourney;
    private float savedStepCount;
    private float createStepSave;

    // ottaa tallennetut askeleet, kävellyt askeleet ja matkan nimen, ja päivittää user interfacen
    public void RefreshUI(){
        SharedPreferences prefGet = getSharedPreferences("StepsPref", Activity.MODE_PRIVATE);
        savedStepCount = prefGet.getFloat("StepKey", 0);
        SharedPreferences prefGet2 = getSharedPreferences("StepsPref", Activity.MODE_PRIVATE);
        savedJourney = prefGet2.getInt("indexKey", 0);

        TextView tv = findViewById(R.id.number);
        tv.setText(String.format("%.1f", Singleton.getInstance().getMatkat(savedJourney).getMatka()
                - ((savedStepCount + stepCount) * 0.0007f)));

        TextView tv2 = findViewById(R.id.destination);
        tv2.setText(Singleton.getInstance().getMatkat(savedJourney).getNimi());
    }

    Animation whiteAnimEnabled, whiteAnimDisabled, textAnimEnabled, textAnimDisabled, textAnimLoad;
    LinearLayout white, text;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.textbox);
        white = findViewById(R.id.white);

        // asetetaan valikko poissaolevaksi sovelluksessa
        white.setVisibility(View.GONE);

        // loadataan valmiiksi tehdyt animaatiot niiden omista tiedostoista
        new AnimationUtils();
        whiteAnimEnabled = AnimationUtils.loadAnimation(this, R.anim.white_animation_enabled);
        whiteAnimDisabled = AnimationUtils.loadAnimation(this, R.anim.white_animation_disabled);
        textAnimEnabled = AnimationUtils.loadAnimation(this, R.anim.text_animation_enabled);
        textAnimDisabled = AnimationUtils.loadAnimation(this, R.anim.text_animation_disabled);
        textAnimLoad = AnimationUtils.loadAnimation(this, R.anim.text_animation_load);

        // aloitetaan tekstin animaatio
        text.startAnimation(textAnimLoad);

        // togglenappula joka avaa ja asettaa valikon näkyväksi sekä sulkee valikon ja asettaa sen poissaolevaksi
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

        ListView lv = findViewById(R.id.lista);
        lv.setAdapter(new ArrayAdapter<Journey>(
                this,
                R.layout.centerlist,
                Singleton.getInstance().getMatkat())
        );

        // listan nappulanpainon jälkeen päivittää user interfacen oikeilla tiedoilla
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialog);
                dialog.setCancelable(false);
                dialog.setTitle("THIS WILL RESET YOUR PROGRESS");
                dialog.setMessage("Do you still want to change your current quest?");
                dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        createStepSave = 0;
                        SharedPreferences prefPut = getSharedPreferences("StepsPref", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor prefEditor = prefPut.edit();
                        prefEditor.putInt("indexKey", i);
                        prefEditor.putFloat("StepKey", createStepSave);
                        prefEditor.commit();
                        RefreshUI();
                    }
                })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                            public void onClick(DialogInterface dialog, int which) {
                                RefreshUI();
                            }
                        });

                final AlertDialog alert = dialog.create();
                alert.show();
            }
        });

        // pyytää käyttäjältä lupaa käyttää askelsensoria
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 101);
        } else {
            Log.d("sensor.permission", "denied");
        }

        // vastaanottaa koko sensoripalvelun puhelimen sisäisistä metodeista ja rekisteröi sen
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        stepCount = event.values[0];
        RefreshUI();
    }

    // pitää olla tuntemattomista syistä, muuten ei toimi tai ainakin valittaa jostain
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, sensorManager.SENSOR_DELAY_NORMAL);
        RefreshUI();
    }

    // pitää pitää vaikkei käytä mitään(?)
    @Override
    protected void onPause() {
        super.onPause();
    }

    // yhdistää kävellyt askeleet käveltyihin askeleihin ja lopettaa sensorin käytön
    @Override
    protected void onDestroy() {
        super.onDestroy();
        createStepSave = stepCount + savedStepCount;
        SharedPreferences prefPut = getSharedPreferences("StepsPref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();
        prefEditor.putFloat("StepKey", createStepSave);
        prefEditor.commit();
        sensorManager.unregisterListener(this, sensor);
    }
}