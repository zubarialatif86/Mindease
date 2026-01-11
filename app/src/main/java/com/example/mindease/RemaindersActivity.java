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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;

import java.util.Calendar;

public class RemaindersActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "mindease_reminders";
    private static final int NOTIFICATION_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remainders);

        // Request notification permission for Android 13+
        requestNotificationPermission();
        
        // Create notification channel for Android 8.0+
        createNotificationChannel();

        // Initialize buttons using IDs from activity_remainders.xml
        MaterialButton hydrationBtn = findViewById(R.id.hydrationBtn);
        MaterialButton breathingBtn = findViewById(R.id.breathingBtn);
        MaterialButton moodBtn = findViewById(R.id.moodBtn);
        MaterialButton sleepBtn = findViewById(R.id.sleepBtn);
        MaterialButton stretchBtn = findViewById(R.id.stretchBtn);
        MaterialButton detoxBtn = findViewById(R.id.detoxBtn);

        // Set click listeners for reminders
        if (hydrationBtn != null) {
            hydrationBtn.setOnClickListener(v ->
                    scheduleReminder(9, 0, "Pani piya? 💧\nTime to drink water 💦")
            );
        }

        if (breathingBtn != null) {
            breathingBtn.setOnClickListener(v ->
                    scheduleReminder(14, 30, "2 min deep breathing 🌬️\nPause & exhale slowly 😌")
            );
        }

        if (moodBtn != null) {
            moodBtn.setOnClickListener(v ->
                    scheduleReminder(20, 0, "Aaj ka mood record karo 📝\nHow are you feeling right now? 😊")
            );
        }

        if (sleepBtn != null) {
            sleepBtn.setOnClickListener(v ->
                    scheduleReminder(22, 0, "Time to wind down 😴\nPrepare for a restful sleep.")
            );
        }

        if (stretchBtn != null) {
            stretchBtn.setOnClickListener(v ->
                    scheduleReminder(11, 0, "Time to stretch! 🧘\nTake a quick break and move your body.")
            );
        }

        if (detoxBtn != null) {
            detoxBtn.setOnClickListener(v ->
                    scheduleReminder(18, 0, "Digital Detox Time 📵\nPut your phone away and relax.")
            );
        }
    }

    private void scheduleReminder(int hour, int minute, String message) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        // If the time has already passed today, schedule it for tomorrow
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        Intent intent = new Intent(this, RemainderBroadcast.class);
        intent.putExtra("message", message);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                message.hashCode(), // Unique ID for each reminder type
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    if (alarmManager.canScheduleExactAlarms()) {
                        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    } else {
                        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    }
                } else {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
                Toast.makeText(this, "Reminder scheduled for " + String.format("%02d:%02d", hour, minute) + " ⏰", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                // Fallback for any security exceptions regarding exact alarms
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                Toast.makeText(this, "Reminder set! ⏰", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "MindEase Reminders",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("MindEase scheduled reminders");
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        NOTIFICATION_PERMISSION_CODE);
            }
        }
    }
}
