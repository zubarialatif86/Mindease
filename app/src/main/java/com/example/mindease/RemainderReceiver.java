package com.example.mindease;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

 class RemaindersActivity extends AppCompatActivity {

    private static final int NOTIFICATION_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remainders);

        // Permission check for Android 13+
        requestNotificationPermission();

        // Buttons ko initialize karna
        Button hydrationBtn = findViewById(R.id.hydrationBtn);
        Button breathingBtn = findViewById(R.id.breathingBtn);
        Button moodBtn = findViewById(R.id.moodBtn);
        Button stressBtn = findViewById(R.id.stressBtn);
        Button positiveBtn = findViewById(R.id.positiveBtn);

        // Click Listeners set karna
        hydrationBtn.setOnClickListener(v -> showToast("Hydration Reminder Activated! 💧"));
        breathingBtn.setOnClickListener(v -> showToast("Breathing & Calm Reminder Set! 🌬️"));
        moodBtn.setOnClickListener(v -> showToast("Daily Mood Check Scheduled! 📝"));
        stressBtn.setOnClickListener(v -> showToast("Stress Relief Break Reminder Set! ⏳"));
        positiveBtn.setOnClickListener(v -> showToast("Positive Affirmations Reminder Set! ✨"));
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        NOTIFICATION_PERMISSION_CODE);
            }
        }
    }
}