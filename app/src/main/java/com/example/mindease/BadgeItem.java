package com.example.mindease;

public class BadgeItem {
    private String title;
    private int imageRes;

    public BadgeItem(String title, int imageRes) {
        this.title = title;
        this.imageRes = imageRes;
    }

    public String getTitle() { return title; }
    public int getImageRes() { return imageRes; }
}
