package com.example.creative_learn.models;

public class CourseContent {
    private final String title;
    private final String description;
    private final String videoFileName;
    private final int videoResourceId;

    public CourseContent(String title, String description, String videoFileName, int videoResourceId) {
        this.title = title;
        this.description = description;
        this.videoFileName = videoFileName;
        this.videoResourceId = videoResourceId;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getVideoFileName() { return videoFileName; }
    public int getVideoResourceId() { return videoResourceId; }
} 