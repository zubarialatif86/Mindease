package com.example.mindease;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class AnalyticsActivity extends AppCompatActivity {

    private LineChart lineChart;
    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        lineChart = findViewById(R.id.lineChart);
        barChart = findViewById(R.id.barChart);

        setupLineChart();
        setupBarChart();
    }

    private void setupLineChart() {
        // Sample weekly stress data
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(1, 3));
        entries.add(new Entry(2, 5));
        entries.add(new Entry(3, 2));
        entries.add(new Entry(4, 6));
        entries.add(new Entry(5, 4));
        entries.add(new Entry(6, 7));
        entries.add(new Entry(7, 5));

        LineDataSet dataSet = new LineDataSet(entries, "Stress Level");
        dataSet.setColor(Color.parseColor("#FF5722"));
        dataSet.setCircleColor(Color.parseColor("#FF5722"));
        dataSet.setLineWidth(2f);
        dataSet.setCircleRadius(5f);
        dataSet.setValueTextSize(12f);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.getDescription().setEnabled(false);
        lineChart.getAxisRight().setEnabled(false);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(10f);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);

        lineChart.animateXY(1000, 1000);
        lineChart.invalidate();
    }

    private void setupBarChart() {
        // Sample monthly anxiety data
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1, 4));
        entries.add(new BarEntry(2, 6));
        entries.add(new BarEntry(3, 3));
        entries.add(new BarEntry(4, 7));
        entries.add(new BarEntry(5, 5));
        entries.add(new BarEntry(6, 8));
        entries.add(new BarEntry(7, 6));
        entries.add(new BarEntry(8, 5));
        entries.add(new BarEntry(9, 7));
        entries.add(new BarEntry(10, 6));
        entries.add(new BarEntry(11, 8));
        entries.add(new BarEntry(12, 7));

        BarDataSet dataSet = new BarDataSet(entries, "Anxiety Level");
        dataSet.setColor(Color.parseColor("#3F51B5"));
        dataSet.setValueTextSize(12f);

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);
        barChart.getDescription().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(10f);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);

        barChart.animateY(1000);
        barChart.invalidate();
    }
}
