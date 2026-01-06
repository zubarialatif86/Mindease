package com.example.mindease;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;

public class HomeActivity extends AppCompatActivity {

    private SeekBar stressSeekBar;
    private TextView stressValue;
    private Button remindersBtn, checkInBtn;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 1. Views Initialization
        stressSeekBar = findViewById(R.id.stressSeekBar);
        stressValue = findViewById(R.id.stressValue);
        remindersBtn = findViewById(R.id.remindersBtn);
        checkInBtn = findViewById(R.id.checkInBtn);
        bottomNav = findViewById(R.id.bottomNav);

        // 2. Stress Slider Logic
        stressSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                stressValue.setText("Level: " + progress);
                // Dynamic Color Change based on stress level
                if (progress <= 3) {
                    stressValue.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                } else if (progress <= 7) {
                    stressValue.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
                } else {
                    stressValue.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                }
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        // 3. Reminders Button Logic
        remindersBtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, RemaindersActivity.class);
            startActivity(intent);
        });

        // 4. Check-In Button Logic (Journal Page)
        checkInBtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, JournalActivity.class);
            startActivity(intent);
        });

        // 5. Bottom Navigation Logic (Handling All Buttons Including Profile)
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                // Already on Home, do nothing
                return true;
            } else if (itemId == R.id.nav_calm) {
                startActivity(new Intent(HomeActivity.this, CalmToolsActivity.class));
                return true;
            } else if (itemId == R.id.nav_journal) {
                startActivity(new Intent(HomeActivity.this, JournalActivity.class));
                return true;
            } else if (itemId == R.id.nav_analytics) {
                startActivity(new Intent(HomeActivity.this, AnalyticsActivity.class));
                return true;
            } else if (itemId == R.id.nav_achievements) {
                startActivity(new Intent(HomeActivity.this, AchievementsActivity.class));
                return true;
            } else if (itemId == R.id.nav_profile) {
                // YEH SECTION PROFILE KE LIYE HAI
                startActivity(new Intent(HomeActivity.this, ProfileSettingsActivity.class));
                return true;
            }

            return false;
        });
    }
}