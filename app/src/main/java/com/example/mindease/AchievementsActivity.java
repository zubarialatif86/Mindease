package com.example.mindease;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

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

        // Fetch user data (mocked for now, in real app fetch from DB/Prefs)
        int completedJournals = 5; 
        int meditationSessions = 3;
        int currentStreak = 7;

        streakText.setText("🔥 " + currentStreak + "-Day Calm Streak");

        // Prepare badges
        badgeList = new ArrayList<>();
        
        // Always available badges (Milestones)
        badgeList.add(new BadgeItem("First Entry", R.drawable.badge_meditation));
        badgeList.add(new BadgeItem("7-Day Calm", R.drawable.badge_calm));
        badgeList.add(new BadgeItem("Stress Buster", R.drawable.badge_stress));
        
        // Conditional badges (Reward System)
        if (completedJournals >= 5) {
            badgeList.add(new BadgeItem("Journal Pro", R.drawable.badge_master));
            Toast.makeText(this, "New Badge Unlocked: Journal Pro! 🏆", Toast.LENGTH_LONG).show();
        }
        
        if (meditationSessions >= 3) {
            badgeList.add(new BadgeItem("Mindful Zen", R.drawable.badge_meditation));
        }

        if (currentStreak >= 10) {
            badgeList.add(new BadgeItem("Unstoppable", R.drawable.badge_calm));
        }

        // Setup adapter
        badgeAdapter = new BadgeAdapter(this, badgeList);
        badgesGrid.setAdapter(badgeAdapter);
        
        // Make badges clickable to show requirements
        badgesGrid.setOnItemClickListener((parent, view, position, id) -> {
            BadgeItem clickedItem = badgeList.get(position);
            Toast.makeText(this, "Badge: " + clickedItem.getTitle() + "\nEarned by staying consistent!", Toast.LENGTH_SHORT).show();
        });
    }
}
