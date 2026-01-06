package com.example.mindease;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class JournalActivity extends AppCompatActivity {

    private LinearLayout moodSelector;
    private EditText journalEditText;
    private Button saveJournalBtn;
    private String selectedMood = "😄";

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        moodSelector = findViewById(R.id.moodSelector);
        journalEditText = findViewById(R.id.journalEditText);
        saveJournalBtn = findViewById(R.id.saveJournalBtn);

        db = AppDatabase.getInstance(this);

        // Mood selection
        for(int i=0; i < moodSelector.getChildCount(); i++) {
            TextView mood = (TextView) moodSelector.getChildAt(i);
            mood.setOnClickListener(v -> {
                selectedMood = mood.getText().toString();
                // Highlight selected mood
                for(int j=0;j<moodSelector.getChildCount();j++){
                    moodSelector.getChildAt(j).setAlpha(0.5f);
                }
                mood.setAlpha(1f);
            });
        }

        // Save button click
        saveJournalBtn.setOnClickListener(v -> {
            String content = journalEditText.getText().toString().trim();
            if(content.isEmpty()){
                Toast.makeText(this, "Please write something!", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = "My Journal Entry"; // ya user input
            JournalEntry entry = new JournalEntry(title, content, selectedMood, System.currentTimeMillis());

            db.journalDao().insert(entry);

            Toast.makeText(this, "Journal saved! ✅", Toast.LENGTH_SHORT).show();
            journalEditText.setText("");
        });
    }
}

