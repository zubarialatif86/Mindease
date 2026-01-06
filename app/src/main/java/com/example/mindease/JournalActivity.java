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

        // Database instance initialize
        db = AppDatabase.getInstance(this);

        moodSelector = findViewById(R.id.moodSelector);
        journalEditText = findViewById(R.id.journalEditText);
        saveJournalBtn = findViewById(R.id.saveJournalBtn);

        // Mood selection logic
        for(int i=0; i < moodSelector.getChildCount(); i++) {
            TextView mood = (TextView) moodSelector.getChildAt(i);
            mood.setOnClickListener(v -> {
                selectedMood = mood.getText().toString();
                for(int j=0; j < moodSelector.getChildCount(); j++){
                    moodSelector.getChildAt(j).setAlpha(0.5f);
                }
                mood.setAlpha(1f);
            });
        }

        saveJournalBtn.setOnClickListener(v -> {
            String content = journalEditText.getText().toString().trim();
            if(content.isEmpty()){
                Toast.makeText(this, "Please write something!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Data ko database mein save karna
            new Thread(() -> {
                JournalEntry entry = new JournalEntry(content, selectedMood, 5, System.currentTimeMillis());
                db.journalDao().insert(entry);

                runOnUiThread(() -> {
                    Toast.makeText(this, "Journal saved locally! ✅", Toast.LENGTH_SHORT).show();
                    journalEditText.setText("");
                    finish(); // Entry save hone ke baad screen close
                });
            }).start();
        });
    }
}