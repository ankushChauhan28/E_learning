package com.example.creative_learn;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.creative_learn.database.DatabaseHelper;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText emailInput, newPasswordInput, confirmPasswordInput;
    private LinearLayout passwordLayout;
    private Button verifyButton, resetButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        databaseHelper = new DatabaseHelper(this);
        
        emailInput = findViewById(R.id.emailInput);
        newPasswordInput = findViewById(R.id.newPasswordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
        passwordLayout = findViewById(R.id.passwordLayout);
        verifyButton = findViewById(R.id.verifyButton);
        resetButton = findViewById(R.id.resetButton);

        verifyButton.setOnClickListener(v -> verifyEmail());
        resetButton.setOnClickListener(v -> resetPassword());
    }

    private void verifyEmail() {
        String email = emailInput.getText().toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (databaseHelper.checkEmail(email)) {
            emailInput.setEnabled(false);
            verifyButton.setVisibility(View.GONE);
            passwordLayout.setVisibility(View.VISIBLE);
            resetButton.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, "Email not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetPassword() {
        String email = emailInput.getText().toString().trim();
        String newPassword = newPasswordInput.getText().toString().trim();
        String confirmPassword = confirmPasswordInput.getText().toString().trim();

        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (databaseHelper.resetPassword(email, newPassword)) {
            Toast.makeText(this, "Password reset successful", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Password reset failed", Toast.LENGTH_SHORT).show();
        }
    }
} 