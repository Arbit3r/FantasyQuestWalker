package com.example.fantasyquestwalker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    // splashscreen kestää 2,5 sekuntia
    private static int SPLASH = 2500;

    Animation startTextAnim;
    TextView text;

    // animaatio, jonka jälkeen vaihtaa mainactivityyn
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        text = findViewById(R.id.quest);

        new AnimationUtils();
        startTextAnim = AnimationUtils.loadAnimation(this, R.anim.splash_text_animation);
        text.startAnimation(startTextAnim);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(Splash.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }, SPLASH);
    }
}