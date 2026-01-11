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

        // Detect swipe on the last page to go to main screen
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            private boolean isLastPageSwiped = false;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == onboardingItems.size() - 1 && positionOffset == 0 && positionOffsetPixels == 0) {
                    if (isLastPageSwiped) {
                        navigateToMain();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (viewPager.getCurrentItem() == onboardingItems.size() - 1 && state == ViewPager2.SCROLL_STATE_DRAGGING) {
                    isLastPageSwiped = true;
                } else if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    isLastPageSwiped = false;
                }
            }
        });

        skipButton.setOnClickListener(v -> navigateToMain());
    }

    private void navigateToMain() {
        Intent intent = new Intent(OnboardingActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}