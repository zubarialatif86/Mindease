package com.example.mindease;

import android.os.Bundle;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.android.material.button.MaterialButton;
import java.util.Random;

public class CalmToolsActivity extends AppCompatActivity {

    private MaterialButton panicBtn;
    private TextView breathingText;
    private View breathingCircle;
    private CardView btnZenMode, btnNatureSounds, btnAffirmations, btnQuickTip;
    private Vibrator vibrator;
    private boolean isBreathing = false;

    private final String[] affirmations = {
        "I am at peace with my past.",
        "I deserve to be happy and healthy.",
        "My mind is calm and my body is relaxed.",
        "I am capable of handling whatever comes my way.",
        "I choose to focus on what I can control."
    };

    private final String[] wellnessTips = {
        "Drink a glass of water slowly to ground yourself.",
        "Try the 5-4-3-2-1 grounding technique.",
        "A 5-minute walk can significantly boost your mood.",
        "Close your eyes and focus only on the sounds around you.",
        "Stretch your neck and shoulders to release tension."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calm_tools);

        // Initialize Views
        panicBtn = findViewById(R.id.panicBtn);
        breathingText = findViewById(R.id.breathingText);
        breathingCircle = findViewById(R.id.breathingCircle);
        btnZenMode = findViewById(R.id.btnZenMode);
        btnNatureSounds = findViewById(R.id.btnNatureSounds);
        btnAffirmations = findViewById(R.id.btnAffirmations);
        btnQuickTip = findViewById(R.id.btnQuickTip);
        
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        // Breathing Tool Click
        breathingCircle.setOnClickListener(v -> toggleBreathing());

        // Card Tool Clicks
        btnZenMode.setOnClickListener(v -> showSimpleDialog("Zen Mode", "Your device is now in Zen Mode. Focus on your breath and let go of all distractions."));
        btnNatureSounds.setOnClickListener(v -> Toast.makeText(this, "Playing soothing nature soundscapes... 🍃", Toast.LENGTH_SHORT).show());
        btnAffirmations.setOnClickListener(v -> showAffirmation());
        btnQuickTip.setOnClickListener(v -> showQuickTip());

        // Panic Button Logic
        panicBtn.setOnClickListener(view -> {
            breathingText.setText("🚨 Panic Mode: Breathe with the Circle");
            Toast.makeText(CalmToolsActivity.this, "Stay Calm! Focus on slow breathing. 🚨", Toast.LENGTH_LONG).show();
            if (!isBreathing) toggleBreathing();

            if (vibrator != null && vibrator.hasVibrator()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    vibrator.vibrate(500);
                }
            }
        });
    }

    private void toggleBreathing() {
        if (!isBreathing) {
            startBreathingAnimation();
            isBreathing = true;
        } else {
            breathingCircle.clearAnimation();
            breathingText.setText("Tap to Start");
            isBreathing = false;
        }
    }

    private void startBreathingAnimation() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                1f, 1.5f, 1f, 1.5f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        
        scaleAnimation.setDuration(4000); // 4 seconds inhale
        scaleAnimation.setRepeatCount(Animation.INFINITE);
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Simplified text update based on animation repeat
                if (breathingText.getText().toString().contains("Inhale")) {
                    breathingText.setText("Exhale... 🌬️");
                } else {
                    breathingText.setText("Inhale... 🧘");
                }
            }
        });

        breathingCircle.startAnimation(scaleAnimation);
        breathingText.setText("Inhale... 🧘");
    }

    private void showAffirmation() {
        String affirmation = affirmations[new Random().nextInt(affirmations.length)];
        showSimpleDialog("Daily Affirmation", affirmation);
    }

    private void showQuickTip() {
        String tip = wellnessTips[new Random().nextInt(wellnessTips.length)];
        showSimpleDialog("Wellness Tip", tip);
    }

    private void showSimpleDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}