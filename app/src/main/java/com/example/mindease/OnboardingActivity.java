package com.example.mindease;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.ArrayList;
import java.util.List;

public class OnboardingActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private WormDotsIndicator dotsIndicator;
    private TextView skipButton;
    private OnboardingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        viewPager = findViewById(R.id.onboardingViewPager);
        dotsIndicator = findViewById(R.id.dotsIndicator);
        skipButton = findViewById(R.id.skipButton);

        List<OnboardingItem> onboardingItems = new ArrayList<>();
        onboardingItems.add(new OnboardingItem(R.drawable.onboarding_bg_1, "Track Your Mood", "Understand your stress & anxiety patterns easily."));
        onboardingItems.add(new OnboardingItem(R.drawable.onboarding_bg_2, "Calm Your Mind", "Use breathing exercises and relaxation tools."));
        onboardingItems.add(new OnboardingItem(R.drawable.onboarding_bg_3, "Feel Better Daily", "Log your thoughts and track your progress."));

        adapter = new OnboardingAdapter(onboardingItems);
        viewPager.setAdapter(adapter);
        dotsIndicator.setViewPager2(viewPager);

        skipButton.setOnClickListener(v -> navigateToMain());
    }

    private void navigateToMain() {
        Intent intent = new Intent(OnboardingActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
