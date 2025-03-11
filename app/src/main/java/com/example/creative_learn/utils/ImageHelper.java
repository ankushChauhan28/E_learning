package com.example.creative_learn.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ImageHelper {
    private static final String TAG = "ImageHelper";

    public static String saveProfileImage(Context context, Uri imageUri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                context.getContentResolver(), imageUri);
            return saveProfileImage(context, bitmap);
        } catch (IOException e) {
            Log.e(TAG, "Error saving profile image", e);
            return null;
        }
    }

    public static String saveProfileImage(Context context, Bitmap bitmap) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", 
            Locale.getDefault()).format(new Date());
        String imageFileName = "PROFILE_" + timeStamp + ".jpg";

        File storageDir = new File(context.getFilesDir(), "profile_images");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }

        File imageFile = new File(storageDir, imageFileName);
        try (FileOutputStream out = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            return imageFile.getAbsolutePath();
        } catch (IOException e) {
            Log.e(TAG, "Error saving bitmap", e);
            return null;
        }
    }

    public static void deleteOldProfileImage(Context context, String oldImagePath) {
        if (oldImagePath != null) {
            File oldImage = new File(oldImagePath);
            if (oldImage.exists()) {
                oldImage.delete();
            }
        }
    }
} 