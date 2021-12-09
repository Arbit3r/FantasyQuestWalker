package com.example.fantasyquestwalker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Animation textAnim;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.quest);

        new AnimationUtils();
        textAnim = AnimationUtils.loadAnimation(this, R.anim.text_animation);
        text.startAnimation(textAnim);
    }
}