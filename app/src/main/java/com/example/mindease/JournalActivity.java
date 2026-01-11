package com.example.mindease;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.android.material.button.MaterialButton;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class JournalActivity extends AppCompatActivity {

    private GridLayout moodSelector;
    private CardView moodSelectorCard;
    private TextView tvSelectMood, tvStressValue;
    private EditText journalEditText;
    private SeekBar stressSeekBar;
    private MaterialButton saveJournalBtn, btnTrackMood;
    private String selectedMood = "😄";
    private int selectedStressLevel = 5;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        db = AppDatabase.getInstance(this);

        moodSelector = findViewById(R.id.moodSelector);
        moodSelectorCard = findViewById(R.id.moodSelectorCard);
        tvSelectMood = findViewById(R.id.tvSelectMood);
        tvStressValue = findViewById(R.id.tvStressValue);
        journalEditText = findViewById(R.id.journalEditText);
        stressSeekBar = findViewById(R.id.stressSeekBar);
        saveJournalBtn = findViewById(R.id.saveJournalBtn);
        btnTrackMood = findViewById(R.id.btnTrackMood);

        // Toggle mood selector
        tvSelectMood.setOnClickListener(v -> {
            moodSelectorCard.setVisibility(moodSelectorCard.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        });

        // Mood selection
        for (int i = 0; i < moodSelector.getChildCount(); i++) {
            View view = moodSelector.getChildAt(i);
            if (view instanceof TextView) {
                TextView emojiTv = (TextView) view;
                emojiTv.setOnClickListener(v -> {
                    selectedMood = emojiTv.getText().toString();
                    tvSelectMood.setText("Mood: " + selectedMood);
                    moodSelectorCard.setVisibility(View.GONE);
                });
            }
        }

        // Stress level selection
        stressSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                selectedStressLevel = progress;
                tvStressValue.setText("Current Level: " + progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Track Mood Button: Shows a popup with the current selection
        btnTrackMood.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Mood Tracking")
                    .setMessage("You are currently feeling: " + selectedMood + 
                                "\nStress Level: " + selectedStressLevel + "/10")
                    .setPositiveButton("OK", null)
                    .show();
        });

        saveJournalBtn.setOnClickListener(v -> {
            String content = journalEditText.getText().toString().trim();
            if (content.isEmpty()) {
                Toast.makeText(this, "Please write your thoughts!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save mood for home screen display
            String todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            SharedPreferences prefs = getSharedPreferences("MindEasePrefs", MODE_PRIVATE);
            prefs.edit().putString("today_mood_" + todayDate, selectedMood).apply();

            new Thread(() -> {
                JournalEntry entry = new JournalEntry(content, selectedMood, selectedStressLevel, System.currentTimeMillis());
                db.journalDao().insert(entry);
                runOnUiThread(() -> {
                    Toast.makeText(this, "Entry saved! ✅", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }).start();
        });
    }
}