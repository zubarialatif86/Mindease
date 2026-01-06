package com.example.mindease;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private SeekBar stressSeekBar;
    private TextView stressValue;
    private Button remindersBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        stressSeekBar = findViewById(R.id.stressSeekBar);
        stressValue = findViewById(R.id.stressValue);
        remindersBtn = findViewById(R.id.remindersBtn);

        // 🔹 Stress Slider Logic
        stressSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                stressValue.setText("Level: " + progress);

                if (progress <= 3) {
                    stressValue.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                } else if (progress <= 7) {
                    stressValue.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
                } else {
                    stressValue.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        // 🔔 Reminders Button Click
        remindersBtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, RemaindersActivity.class);
            startActivity(intent);
        });
    }
}
