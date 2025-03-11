package com.example.creative_learn.models;

public class Course {
    private int id;
    private String title;
    private String description;
    private String url;
    private int thumbnailResId;
    private int progress;

    public Course(int id, String title, String description, String url, int thumbnailResId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.url = url;
        this.thumbnailResId = thumbnailResId;
        this.progress = 0;
    }

    public Course(int id, String title, String description, String url, int thumbnailResId, int progress) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.url = url;
        this.thumbnailResId = thumbnailResId;
        this.progress = progress;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getUrl() { return url; }
    public int getThumbnailResId() { return thumbnailResId; }
    public int getProgress() { return progress; }
    public void setProgress(int progress) { this.progress = progress; }
} 