package com.example.creative_learn.ui.profile;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.creative_learn.R;
import com.example.creative_learn.database.DatabaseHelper;
import com.example.creative_learn.utils.SessionManager;

public class ProfileFragment extends Fragment {
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int GALLERY_PERMISSION_CODE = 101;
    private static final int CAMERA_REQUEST_CODE = 102;
    private static final int GALLERY_REQUEST_CODE = 103;

    private ImageView profileImage;
    private TextView nameText, emailText, phoneText;
    private ImageButton editNameButton, editImageButton;
    private DatabaseHelper databaseHelper;
    private SessionManager sessionManager;
    private String currentUserEmail;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        initializeViews(root);
        setupClickListeners();
        loadUserData();

        return root;
    }

    private void initializeViews(View root) {
        profileImage = root.findViewById(R.id.profileImage);
        nameText = root.findViewById(R.id.nameText);
        emailText = root.findViewById(R.id.emailText);
        phoneText = root.findViewById(R.id.phoneText);
        editNameButton = root.findViewById(R.id.editNameButton);
        editImageButton = root.findViewById(R.id.editImageButton);

        databaseHelper = new DatabaseHelper(requireContext());
        sessionManager = new SessionManager(requireContext());
        currentUserEmail = sessionManager.getUserEmail();
    }

    private void setupClickListeners() {
        editNameButton.setOnClickListener(v -> showEditNameDialog());
        editImageButton.setOnClickListener(v -> showImagePickerDialog());
    }

    private void showImagePickerDialog() {
        String[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Change Profile Picture");
        builder.setItems(options, (dialog, which) -> {
            switch (which) {
                case 0: // Take Photo
                    checkCameraPermission();
                    break;
                case 1: // Choose from Gallery
                    checkGalleryPermission();
                    break;
                case 2: // Cancel
                    dialog.dismiss();
                    break;
            }
        });
        builder.show();
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_CODE);
        } else {
            openCamera();
        }
    }

    private void checkGalleryPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    GALLERY_PERMISSION_CODE);
        } else {
            openGallery();
        }
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                         @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == CAMERA_PERMISSION_CODE) {
                openCamera();
            } else if (requestCode == GALLERY_PERMISSION_CODE) {
                openGallery();
            }
        } else {
            Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE && data != null) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                profileImage.setImageBitmap(photo);
                saveProfileImage(photo);
            } else if (requestCode == GALLERY_REQUEST_CODE && data != null) {
                Uri imageUri = data.getData();
                try {
                    Bitmap photo = MediaStore.Images.Media.getBitmap(
                            requireActivity().getContentResolver(), imageUri);
                    profileImage.setImageBitmap(photo);
                    saveProfileImage(photo);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(requireContext(), "Failed to load image", 
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void saveProfileImage(Bitmap photo) {
        // Convert bitmap to string or save to internal storage
        String imageString = convertBitmapToString(photo);
        if (databaseHelper.updateProfileImage(currentUserEmail, imageString)) {
            Toast.makeText(requireContext(), "Profile picture updated", 
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "Failed to update profile picture", 
                    Toast.LENGTH_SHORT).show();
        }
    }

    private String convertBitmapToString(Bitmap bitmap) {
        // Implement bitmap to string conversion (Base64 or file path)
        // Return the string representation
        return ""; // Implement this method based on your storage strategy
    }

    private void loadUserData() {
        // Get user details from database
        String[] userDetails = databaseHelper.getUserDetails(currentUserEmail);
        if (userDetails != null) {
            nameText.setText(userDetails[0]);  // Name
            emailText.setText(currentUserEmail);
            phoneText.setText(userDetails[1]);  // Phone
        }
    }

    private void showEditNameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Edit Name");

        // Set up the input
        final EditText input = new EditText(requireContext());
        input.setText(nameText.getText());
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Save", (dialog, which) -> {
            String newName = input.getText().toString().trim();
            if (!newName.isEmpty()) {
                if (databaseHelper.updateUserName(currentUserEmail, newName)) {
                    nameText.setText(newName);
                    Toast.makeText(requireContext(), "Name updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Failed to update name", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
} 