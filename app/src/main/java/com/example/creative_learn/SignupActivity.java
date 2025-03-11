package com.example.creative_learn;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.creative_learn.database.DatabaseHelper;
import com.example.creative_learn.utils.SessionManager;

public class SignupActivity extends AppCompatActivity {
    private EditText nameInput, phoneInput, emailInput, passwordInput;
    private DatabaseHelper databaseHelper;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        databaseHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        nameInput = findViewById(R.id.nameInput);
        phoneInput = findViewById(R.id.phoneInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        Button signupButton = findViewById(R.id.signupButton);
        TextView loginPrompt = findViewById(R.id.loginPrompt);

        signupButton.setOnClickListener(v -> performSignup());
        loginPrompt.setOnClickListener(v -> finish());
    }

    private boolean validateInput() {
        String name = nameInput.getText().toString().trim();
        String phone = phoneInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (name.isEmpty()) {
            nameInput.setError("Name is required");
            return false;
        }

        if (phone.isEmpty()) {
            phoneInput.setError("Phone number is required");
            return false;
        }

        if (phone.length() < 10) {
            phoneInput.setError("Enter valid phone number");
            return false;
        }

        if (email.isEmpty()) {
            emailInput.setError("Email is required");
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Enter valid email");
            return false;
        }

        if (password.isEmpty()) {
            passwordInput.setError("Password is required");
            return false;
        }

        if (password.length() < 6) {
            passwordInput.setError("Password must be at least 6 characters");
            return false;
        }

        return true;
    }

    private void performSignup() {
        if (!validateInput()) {
            return;
        }

        String name = nameInput.getText().toString().trim();
        String phone = phoneInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (databaseHelper.registerUser(name, phone, email, password)) {
            sessionManager.setLogin(true, email);
            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finishAffinity();
        } else {
            Toast.makeText(this, "Email already exists", Toast.LENGTH_SHORT).show();
        }
    }
} 