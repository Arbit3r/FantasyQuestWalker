package com.example.fantasyquestwalker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    Animation whiteAnimEnabled, whiteAnimDisabled, textAnimEnabled, textAnimDisabled, textAnimLoad;
    LinearLayout white, text;

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
    }
}