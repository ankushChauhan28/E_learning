package com.example.creative_learn.models;

public class Category {
    private int id;
    private String name;
    private int iconResId;
    private int courseCount;

    public Category(int id, String name, int iconResId, int courseCount) {
        this.id = id;
        this.name = name;
        this.iconResId = iconResId;
        this.courseCount = courseCount;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getIconResId() { return iconResId; }
    public int getCourseCount() { return courseCount; }
} 