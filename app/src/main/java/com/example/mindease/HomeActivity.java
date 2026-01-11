package com.example.mindease;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    private ImageButton profileBtn;
    private CardView cardJournal, cardAnalytics, cardCalmTools, cardAchievements, cardReminders, cardDashboard;
    private CardView cardMeditation, cardMoodTracker, cardHelp, cardMoodHistory;
    private TextView welcomeText, tvTodayMood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Enable immersive mode
        hideSystemUI();

        // 1. Initialize Views
        profileBtn = findViewById(R.id.profileBtn);
        cardJournal = findViewById(R.id.cardJournal);
        cardAnalytics = findViewById(R.id.cardAnalytics);
        cardCalmTools = findViewById(R.id.cardCalmTools);
        cardAchievements = findViewById(R.id.cardAchievements);
        cardReminders = findViewById(R.id.cardReminders);
        cardDashboard = findViewById(R.id.cardDashboard);
        
        cardMeditation = findViewById(R.id.cardMeditation);
        cardMoodTracker = findViewById(R.id.cardMoodTracker);
        cardHelp = findViewById(R.id.cardHelp);
        cardMoodHistory = findViewById(R.id.cardMoodHistory);
        
        welcomeText = findViewById(R.id.welcomeText);
        tvTodayMood = findViewById(R.id.tvTodayMood);

        // 2. Profile Button Logic
        if (profileBtn != null) {
            profileBtn.setOnClickListener(v -> {
                startActivity(new Intent(HomeActivity.this, ProfileSettingsActivity.class));
            });
        }

        // 3. Navigation Logic
        if (cardJournal != null) {
            cardJournal.setOnClickListener(v -> {
                new AlertDialog.Builder(this)
                        .setTitle("Wellness Journaling")
                        .setMessage("Maintaining a journal is a powerful tool for your health:\n\n" +
                                "• Reduces stress and anxiety\n" +
                                "• Helps track symptoms and triggers daily\n" +
                                "• Provides an opportunity for positive self-talk\n" +
                                "• Identifies negative thoughts and behaviors\n\n" +
                                "Would you like to write an entry now?")
                        .setPositiveButton("Write Entry", (dialog, which) -> {
                            startActivity(new Intent(this, JournalActivity.class));
                        })
                        .setNegativeButton("Maybe Later", null)
                        .show();
            });
        }

        if (cardAnalytics != null) cardAnalytics.setOnClickListener(v -> startActivity(new Intent(this, AnalyticsActivity.class)));
        if (cardCalmTools != null) cardCalmTools.setOnClickListener(v -> startActivity(new Intent(this, CalmToolsActivity.class)));
        if (cardAchievements != null) cardAchievements.setOnClickListener(v -> startActivity(new Intent(this, AchievementsActivity.class)));
        if (cardReminders != null) cardReminders.setOnClickListener(v -> startActivity(new Intent(this, RemaindersActivity.class)));
        if (cardDashboard != null) cardDashboard.setOnClickListener(v -> startActivity(new Intent(this, DashboardActivity.class)));

        if (cardMeditation != null) {
            cardMeditation.setOnClickListener(v -> {
                new AlertDialog.Builder(this)
                        .setTitle("Guided Meditation")
                        .setMessage("Meditation is a practice where an individual uses a technique – such as mindfulness, or focusing the mind on a particular object, thought, or activity – to train attention and awareness, and achieve a mentally clear and emotionally calm and stable state.")
                        .setPositiveButton("Start Practicing", (dialog, which) -> {
                            startActivity(new Intent(this, CalmToolsActivity.class));
                        })
                        .show();
            });
        }

        if (cardMoodTracker != null) {
            cardMoodTracker.setOnClickListener(v -> {
                startActivity(new Intent(this, JournalActivity.class));
            });
        }

        if (cardMoodHistory != null) {
            cardMoodHistory.setOnClickListener(v -> {
                startActivity(new Intent(this, JournalHistoryActivity.class));
            });
        }

        if (cardHelp != null) {
            cardHelp.setOnClickListener(v -> {
                new AlertDialog.Builder(this)
                        .setTitle("MindEase Guide")
                        .setMessage("Welcome to MindEase! \n\n1. Use 'Journal' to write your thoughts.\n2. 'Tools' provides breathing exercises.\n3. 'Analytics' helps you track your progress.\n4. 'Reminders' keeps you on schedule.\n\nStay consistent for better mental health!")
                        .setPositiveButton("Got it!", null)
                        .show();
            });
        }

        updateMoodDisplay();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateMoodDisplay();
        hideSystemUI();
    }

    private void updateMoodDisplay() {
        String todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        SharedPreferences prefs = getSharedPreferences("MindEasePrefs", MODE_PRIVATE);
        String mood = prefs.getString("today_mood_" + todayDate, null);
        if (tvTodayMood != null) {
            if (mood != null) {
                tvTodayMood.setText("Today: " + mood);
            } else {
                tvTodayMood.setText("Mood History");
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}
