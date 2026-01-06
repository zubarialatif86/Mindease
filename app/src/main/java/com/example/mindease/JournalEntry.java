package com.example.mindease;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "journal_table")
public class JournalEntry {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String content;
    public String mood;
    public int stressLevel; // Graph ke liye
    public long timestamp;

    public JournalEntry(String content, String mood, int stressLevel, long timestamp) {
        this.content = content;
        this.mood = mood;
        this.stressLevel = stressLevel;
        this.timestamp = timestamp;
    }
}