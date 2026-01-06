package com.example.mindease;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {JournalEntry.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract JournalDao journalDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "mindease_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries() // Chote data ke liye theek hai
                    .build();
        }
        return instance;
    }
}