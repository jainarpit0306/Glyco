package com.glyco.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private TextInputLayout tilEmail, tilPassword;
    private Button btnLogin;
    private TextView tvForgotPassword, tvSignUp;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeViews();
        setupClickListeners();
    }

    private void initializeViews() {
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        tilEmail = findViewById(R.id.til_email);
        tilPassword = findViewById(R.id.til_password);
        btnLogin = findViewById(R.id.btn_login);
        // Removed forgot password and sign up UI from layout
        progressBar = findViewById(R.id.progress_bar);
    }

    private void setupClickListeners() {
        btnLogin.setOnClickListener(v -> handleLogin());
        // No other actions needed
    }

    private void handleLogin() {
        if (validateForm()) {
            performLogin();
        }
    }

    private boolean validateForm() {
        boolean isValid = true;
        
        // Clear previous errors
        tilEmail.setError(null);
        tilPassword.setError(null);

        // Email validation
        String email = etEmail.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            tilEmail.setError("Email is required");
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError("Please enter a valid email address");
            isValid = false;
        }

        // Password validation
        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            tilPassword.setError("Password is required");
            isValid = false;
        } else if (password.length() < 6) {
            tilPassword.setError("Password must be at least 6 characters");
            isValid = false;
        } else if (password.length() > 50) {
            tilPassword.setError("Password is too long (max 50 characters)");
            isValid = false;
        }

        return isValid;
    }

    private void performLogin() {
        showLoading(true);
        
        // Simulate login process (replace with actual authentication)
        new android.os.Handler().postDelayed(() -> {
            showLoading(false);
            
            // For demo purposes, accept any valid email/password combination
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            
            if (isValidCredentials(email, password)) {
                // Login successful
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
                navigateToMain();
            } else {
                // Login failed
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        }, 2000);
    }

    private boolean isValidCredentials(String email, String password) {
        // Demo validation - replace with actual authentication logic
        // For demo purposes, accept these test credentials:
        // Email: test@example.com, Password: password123
        // Or any email with password length >= 6
        
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            return false;
        }
        
        // Demo credentials for testing
        if (email.equals("test@example.com") && password.equals("password123")) {
            return true;
        }
        
        // Accept any valid email format with password >= 6 characters
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length() >= 6;
    }

    private void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        btnLogin.setEnabled(!show);
        btnLogin.setText(show ? "Logging in..." : "Login");
    }

    private void navigateToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    // Forgot password and signup actions removed per requirements

    @Override
    public void onBackPressed() {
        // Prevent going back to splash screen
        moveTaskToBack(true);
    }
}
