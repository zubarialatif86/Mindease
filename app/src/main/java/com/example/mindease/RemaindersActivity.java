package com.example.mindease;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Calendar;

 class RemaindersReceiver extends AppCompatActivity {

    private static final String CHANNEL_ID = "mindease_reminders";
    private static final int NOTIFICATION_PERMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remainders);

        // 🔔 Android 13+ notification permission
        requestNotificationPermission();

        createNotificationChannel();

        Button hydrationBtn = findViewById(R.id.hydrationBtn);
        Button breathingBtn = findViewById(R.id.breathingBtn);
        Button moodBtn = findViewById(R.id.moodBtn);
        Button stressBtn = findViewById(R.id.stressBtn);
        Button positiveBtn = findViewById(R.id.positiveBtn);

        hydrationBtn.setOnClickListener(v ->
                scheduleReminder(9, 0,
                        "Pani piya? 💧\nTime to drink water 💦")
        );

        breathingBtn.setOnClickListener(v ->
                scheduleReminder(14, 30,
                        "2 min deep breathing 🌬️\nPause & exhale slowly 😌\nTake a 1-min mindfulness break 🧘‍♂️")
        );

        moodBtn.setOnClickListener(v ->
                scheduleReminder(20, 0,
                        "Aaj ka mood record karo 📝\nHow are you feeling right now? 😊😔")
        );

        stressBtn.setOnClickListener(v ->
                scheduleReminder(16, 0,
                        "Step away from stress for 2 mins ⏳\nDo a short grounding exercise 🌿")
        );

        positiveBtn.setOnClickListener(v ->
                scheduleReminder(21, 0,
                        "Great job! Keep tracking your mood ✨\nRemember: Every small step counts 💛")
        );
    }

    // 🔔 Runtime permission request
    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        NOTIFICATION_PERMISSION_CODE
                );
            }
        }
    }

    private void scheduleReminder(int hour, int minute, String message) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        Intent intent = new Intent(this, RemaindersActivity.class);
        intent.putExtra("remainderMessage", message);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                message.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager =
                (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if (alarmManager != null) {
            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    pendingIntent
            );
        }

        Toast.makeText(this,
                "Reminder scheduled successfully ⏰",
                Toast.LENGTH_SHORT).show();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "MindEase Reminders",
                    NotificationManager.IMPORTANCE_HIGH
            );

            channel.setDescription("MindEase scheduled reminders");

            NotificationManager manager =
                    getSystemService(NotificationManager.class);

            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }
}
