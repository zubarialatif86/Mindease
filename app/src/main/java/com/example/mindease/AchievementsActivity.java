package com.example.mindease;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AchievementsActivity extends AppCompatActivity {

    private TextView streakText;
    private GridView badgesGrid;
    private List<BadgeItem> badgeList;
    private BadgeAdapter badgeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        streakText = findViewById(R.id.streakText);
        badgesGrid = findViewById(R.id.badgesGrid);

        // Example: 7-day streak
        streakText.setText("🔥 7-Day Calm Streak");

        // Prepare badges
        badgeList = new ArrayList<>();
        badgeList.add(new BadgeItem("7-Day Calm", R.drawable.badge_calm));
        badgeList.add(new BadgeItem("Consistency Master", R.drawable.badge_master));
        badgeList.add(new BadgeItem("Meditation Beginner", R.drawable.badge_meditation));
        badgeList.add(new BadgeItem("Stress Buster", R.drawable.badge_stress));

        // Setup adapter
        badgeAdapter = new BadgeAdapter(this, badgeList);
        badgesGrid.setAdapter(badgeAdapter);
    }
}
