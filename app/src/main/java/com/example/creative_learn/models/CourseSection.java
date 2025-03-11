package com.example.creative_learn.models;

public class CourseSection {
    private String title;
    private String description;
    private String videoFileName;
    private int videoResourceId;

    public CourseSection(String title, String description, String videoFileName, int videoResourceId) {
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