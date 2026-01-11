package com.example.mindease;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyticsActivity extends AppCompatActivity {

    private LineChart lineChart;
    private BarChart barChart;
    private TextView insightText;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        lineChart = findViewById(R.id.lineChart);
        barChart = findViewById(R.id.barChart);
        insightText = findViewById(R.id.insightText);
        db = AppDatabase.getInstance(this);

        setupCharts();
        loadData();
    }

    private void setupCharts() {
        // Line Chart Setup
        lineChart.getDescription().setEnabled(false);
        lineChart.setDrawGridBackground(false);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.WHITE);
        lineChart.getAxisLeft().setTextColor(Color.WHITE);
        lineChart.getLegend().setTextColor(Color.WHITE);

        // Bar Chart Setup
        barChart.getDescription().setEnabled(false);
        barChart.setDrawGridBackground(false);
        XAxis barXAxis = barChart.getXAxis();
        barXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        barXAxis.setDrawGridLines(false);
        barXAxis.setGranularity(1f);
        barXAxis.setTextColor(Color.WHITE);
        barChart.getAxisLeft().setTextColor(Color.WHITE);
        barChart.getLegend().setTextColor(Color.WHITE);
    }

    private void loadData() {
        new Thread(() -> {
            List<JournalEntry> entries = db.journalDao().getAllEntries();
            
            if (entries == null || entries.isEmpty()) {
                runOnUiThread(() -> insightText.setText("No data yet. Start journaling to see your analytics!"));
                return;
            }

            // Group data by day for Weekly (last 7 entries for simplicity, or by actual days)
            // Monthly grouping
            processWeeklyData(entries);
            processMonthlyData(entries);
            
            updateInsight(entries);
        }).start();
    }

    private void processWeeklyData(List<JournalEntry> entries) {
        List<Entry> lineEntries = new ArrayList<>();
        // Use last 7 entries for the weekly view
        int count = Math.min(entries.size(), 7);
        List<JournalEntry> lastSeven = entries.subList(0, count);
        Collections.reverse(lastSeven);

        String[] days = new String[count];
        for (int i = 0; i < count; i++) {
            JournalEntry e = lastSeven.get(i);
            lineEntries.add(new Entry(i, e.stressLevel));
            days[i] = "Entry " + (i + 1); // Or format timestamp to "Mon", "Tue" etc.
        }

        runOnUiThread(() -> {
            lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(days));
            updateLineChart(lineEntries);
        });
    }

    private void processMonthlyData(List<JournalEntry> entries) {
        // Logic to group by week of the month
        List<BarEntry> barEntries = new ArrayList<>();
        Map<Integer, List<Integer>> weeklyAverages = new HashMap<>();

        Calendar cal = Calendar.getInstance();
        for (JournalEntry entry : entries) {
            cal.setTimeInMillis(entry.timestamp);
            int week = cal.get(Calendar.WEEK_OF_MONTH);
            if (!weeklyAverages.containsKey(week)) {
                weeklyAverages.put(week, new ArrayList<>());
            }
            weeklyAverages.get(week).add(entry.stressLevel);
        }

        List<Integer> sortedWeeks = new ArrayList<>(weeklyAverages.keySet());
        Collections.sort(sortedWeeks);

        String[] weekLabels = new String[sortedWeeks.size()];
        for (int i = 0; i < sortedWeeks.size(); i++) {
            int week = sortedWeeks.get(i);
            List<Integer> levels = weeklyAverages.get(week);
            float sum = 0;
            for (int l : levels) sum += l;
            barEntries.add(new BarEntry(i, sum / levels.size()));
            weekLabels[i] = "Week " + week;
        }

        runOnUiThread(() -> {
            barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(weekLabels));
            updateBarChart(barEntries);
        });
    }

    private void updateLineChart(List<Entry> entries) {
        LineDataSet dataSet = new LineDataSet(entries, "Stress Trend");
        dataSet.setColor(Color.parseColor("#FFCC00")); // Gold-ish color for visibility on space bg
        dataSet.setCircleColor(Color.WHITE);
        dataSet.setLineWidth(3f);
        dataSet.setCircleRadius(5f);
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(Color.parseColor("#FFCC00"));
        dataSet.setFillAlpha(40);
        dataSet.setValueTextColor(Color.WHITE);

        lineChart.setData(new LineData(dataSet));
        lineChart.animateX(1000);
        lineChart.invalidate();
    }

    private void updateBarChart(List<BarEntry> entries) {
        BarDataSet dataSet = new BarDataSet(entries, "Avg Anxiety Level");
        dataSet.setColor(Color.parseColor("#00E5FF")); // Cyan color
        dataSet.setValueTextColor(Color.WHITE);

        barChart.setData(new BarData(dataSet));
        barChart.animateY(1000);
        barChart.invalidate();
    }

    private void updateInsight(List<JournalEntry> entries) {
        float sum = 0;
        for (JournalEntry e : entries) sum += e.stressLevel;
        float avg = sum / entries.size();

        String message;
        if (avg < 3) message = "You're handling things brilliantly! Low stress overall.";
        else if (avg < 6) message = "A bit of tension lately. Try deep breathing or music.";
        else message = "High stress levels detected. Please prioritize rest and meditation.";

        runOnUiThread(() -> insightText.setText("Overall Insight: " + message));
    }
}