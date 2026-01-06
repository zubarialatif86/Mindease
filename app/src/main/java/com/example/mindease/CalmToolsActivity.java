package com.example.mindease;

import android.os.Bundle;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CalmToolsActivity extends AppCompatActivity {

    private Button panicBtn;
    private TextView breathingText;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Correct XML layout
        setContentView(R.layout.activity_calm_tools);

        // Initialize views
        panicBtn = findViewById(R.id.panicBtn);
        breathingText = findViewById(R.id.breathingText); // Use correct TextView ID
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        // Set click listener for panic button
        panicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change the breathing text
                breathingText.setText("🚨 Panic Mode Activated! Stay Calm!");

                // Show a toast message
                Toast.makeText(CalmToolsActivity.this,
                        "Panic Button Clicked! Help is on the way 🚨",
                        Toast.LENGTH_LONG).show();

                // Vibrate phone for 500ms safely
                if (vibrator != null && vibrator.hasVibrator()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        vibrator.vibrate(500);
                    }
                }

                // Optional: Add more actions like opening calm overlay
            }
        });
    }
}
