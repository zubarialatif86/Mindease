package com.example.mindease;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ProfileSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

        // 1. Initialize Views
        CardView editProfileCard = findViewById(R.id.editProfileCard);
        Switch darkModeSwitch = findViewById(R.id.darkModeSwitch);
        Switch notificationsSwitch = findViewById(R.id.notificationsSwitch);
        CardView helpSupportCard = findViewById(R.id.helpSupportCard);
        CardView privacyPolicyCard = findViewById(R.id.privacyPolicyCard);
        CardView aboutCard = findViewById(R.id.aboutCard);
        CardView logoutCard = findViewById(R.id.logoutCard);

        // 2. Button & Switch Listeners

        // Edit Profile Details
        editProfileCard.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("MindEasePrefs", MODE_PRIVATE);
            String userId = prefs.getString("user_id", "Unknown");
            new AlertDialog.Builder(this)
                    .setTitle("Account Details")
                    .setMessage("User ID: " + userId + "\n\nCurrently, profile editing is handled via web dashboard. Stay tuned for mobile updates!")
                    .setPositiveButton("Got it", null)
                    .show();
        });

        // Dark Mode Toggle
        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String status = isChecked ? "Dark Mode Enabled" : "Dark Mode Disabled";
            Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
            // In a real app, you would apply the theme here
        });

        // Notifications Toggle
        notificationsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String status = isChecked ? "Notifications On" : "Notifications Off";
            Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
        });

        // Help & Support
        helpSupportCard.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Help & Support")
                    .setMessage("For any queries, please email us at:\nsupport@mindease.com\n\nOur team is available 24/7.")
                    .setPositiveButton("OK", null)
                    .show();
        });

        // Privacy Policy
        privacyPolicyCard.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Privacy Policy")
                    .setMessage("MindEase respects your privacy. We do not sell your personal data. All your journal entries are stored locally on your device or encrypted in our secure cloud.")
                    .setPositiveButton("Close", null)
                    .show();
        });

        // About MindEase
        aboutCard.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("About MindEase")
                    .setMessage("MindEase v1.0.0\n\nDeveloped to help you manage stress, track your mood, and find inner peace through guided tools and journaling.")
                    .setPositiveButton("Awesome!", null)
                    .show();
        });

        // Logout
        logoutCard.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("Logout", (dialog, which) -> {
                        // Clear user data
                        getSharedPreferences("MindEasePrefs", MODE_PRIVATE).edit().clear().apply();
                        
                        // Go to Login Screen
                        Intent intent = new Intent(ProfileSettingsActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }
}
