package com.example.mindease;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class JournalHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_history);

        recyclerView = findViewById(R.id.journalRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = AppDatabase.getInstance(this);

        // Database se data fetch karna background thread mein
        loadHistory();
    }

    private void loadHistory() {
        new Thread(() -> {
            List<JournalEntry> entries = db.journalDao().getAllEntries(); // Database call

            // UI update hamesha Main Thread par honi chahiye
            runOnUiThread(() -> {
                if (entries != null && !entries.isEmpty()) {
                    JournalAdapter adapter = new JournalAdapter(entries);
                    recyclerView.setAdapter(adapter);
                }
            });
        }).start();
    }
}