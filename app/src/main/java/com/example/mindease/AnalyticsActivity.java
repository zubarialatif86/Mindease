package com.example.mindease;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import java.util.ArrayList;
import java.util.List;

public class AnalyticsActivity extends AppCompatActivity {

    private LineChart lineChart;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        lineChart = findViewById(R.id.lineChart);
        db = AppDatabase.getInstance(this);

        showDataFromDatabase();
    }

    private void showDataFromDatabase() {
        // 1. SQL Database se sara data fetch karna
        List<JournalEntry> entriesFromDb = db.journalDao().getAllEntries();

        List<Entry> chartEntries = new ArrayList<>();

        // 2. Loop chala kar database ka data Graph list mein daalna
        for (int i = 0; i < entriesFromDb.size(); i++) {
            // i = X-axis (din), stressLevel = Y-axis
            chartEntries.add(new Entry(i, entriesFromDb.get(i).stressLevel));
        }

        if (chartEntries.size() > 0) {
            LineDataSet dataSet = new LineDataSet(chartEntries, "Stress History");
            dataSet.setColor(Color.BLUE);
            dataSet.setCircleColor(Color.RED);

            LineData lineData = new LineData(dataSet);
            lineChart.setData(lineData);
            lineChart.invalidate(); // Refresh graph
        }
    }
}