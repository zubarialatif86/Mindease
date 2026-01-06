package com.example.mindease;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Animation ko poori screen (DecorView) par lagate hain taake error na aaye
        View rootView = getWindow().getDecorView().findViewById(android.R.id.content);

        Animation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(2000);
        rootView.startAnimation(fadeIn);

        // 3 Seconds baad doosri activity par jana
        new Handler().postDelayed(() -> {
            try {
                Intent intent = new Intent(SplashActivity.this, OnboardingActivity.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                // Agar koi error aaye to yahan log mein show hoga
                e.printStackTrace();
            }
        }, 3000);
    }
}