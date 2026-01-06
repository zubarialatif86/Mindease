package com.example.mindease;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        LinearLayout layout = findViewById(R.id.logo).getRootView().findViewById(android.R.id.content);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(2000);
        layout.startAnimation(fadeIn);

        new Handler().postDelayed(() -> {
            // NEXT: OnboardingActivity
            Intent intent = new Intent(SplashActivity.this, OnboardingActivity.class);
            startActivity(intent);
            finish();
        }, 3000);
    }
}
