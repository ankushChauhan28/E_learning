package com.example.creative_learn;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.io.File;

public class CreativeLearnApp extends Application {
    private static final String TAG = "CreativeLearnApp";

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize any app-wide configurations here
    }

    public static void cleanupProfileImages(Context context) {
        try {
            File profileImagesDir = new File(context.getFilesDir(), "profile_images");
            if (profileImagesDir.exists()) {
                File[] files = profileImagesDir.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.delete()) {
                            Log.d(TAG, "Deleted: " + file.getName());
                        }
                    }
                }
                profileImagesDir.delete();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error cleaning up profile images", e);
        }
    }
} 