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
        setContentView(R.layout.activity_calm_tools);

        panicBtn = findViewById(R.id.panicBtn);
        breathingText = findViewById(R.id.breathingText);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);



        panicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                breathingText.setText("🚨 Panic Mode Activated! Breathing Exercise Starting...");

                Toast.makeText(CalmToolsActivity.this,
                        "Stay Calm! Sound and Vibration Activated 🚨",
                        Toast.LENGTH_LONG).show();


                // 2. Vibration Logic
                if (vibrator != null && vibrator.hasVibrator()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        vibrator.vibrate(1000);
                    }
                }
            }
        });
    }


    }
