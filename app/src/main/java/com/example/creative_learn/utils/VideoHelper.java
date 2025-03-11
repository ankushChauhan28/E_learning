package com.example.creative_learn.utils;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class VideoHelper {
    private static final String TAG = "VideoHelper";
    private static final String VIDEO_FOLDER = "course_videos";
    private static ProgressListener progressListener;

    public interface ProgressListener {
        void onProgressUpdate(int progress);
    }

    public static void setProgressListener(ProgressListener listener) {
        progressListener = listener;
    }

    public static boolean isVideoDownloaded(Context context, String fileName) {
        File videoFile = new File(context.getFilesDir(), VIDEO_FOLDER + "/" + fileName);
        return videoFile.exists();
    }

    public static String getVideoPath(Context context, String fileName) {
        return new File(context.getFilesDir(), VIDEO_FOLDER + "/" + fileName).getAbsolutePath();
    }

    public static void copyVideoFromRawToInternal(Context context, int resourceId, String fileName) {
        File videoFolder = new File(context.getFilesDir(), VIDEO_FOLDER);
        if (!videoFolder.exists()) {
            videoFolder.mkdirs();
        }

        File videoFile = new File(videoFolder, fileName);
        if (videoFile.exists()) {
            return;
        }

        try {
            InputStream in = context.getResources().openRawResource(resourceId);
            OutputStream out = new FileOutputStream(videoFile);

            byte[] buffer = new byte[1024];
            int totalLength = in.available();
            int totalRead = 0;
            int read;

            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
                totalRead += read;
                updateCopyProgress((int) ((totalRead * 100L) / totalLength));
            }

            in.close();
            out.flush();
            out.close();

            updateCopyProgress(100);
        } catch (IOException e) {
            Log.e(TAG, "Error copying video file", e);
        }
    }

    private static void updateCopyProgress(int progress) {
        if (progressListener != null) {
            progressListener.onProgressUpdate(progress);
        }
    }
} 