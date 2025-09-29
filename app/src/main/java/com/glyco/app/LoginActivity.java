package com.glyco.app;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private ProgressBar progressBar;
    private ImageView ivPasswordToggle;
    private boolean isPasswordVisible = false;
    
    // Animation views
    private MaterialCardView logoContainer;
    private ImageView logoImage;
    private LinearLayout logoSection;
    private TextView tvAppName, tvTagline;
    private LinearLayout loginFormContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeViews();
        setupClickListeners();
        startLogoAnimation();
    }

    private void initializeViews() {
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progress_bar);
        ivPasswordToggle = findViewById(R.id.iv_password_toggle);
        
        // Initialize animation views
        logoSection = findViewById(R.id.logo_section);
        logoContainer = findViewById(R.id.logo_container);
        logoImage = findViewById(R.id.logo_image);
        tvAppName = findViewById(R.id.tv_app_name);
        tvTagline = findViewById(R.id.tv_tagline);
        loginFormContainer = findViewById(R.id.login_form_container);
    }

    private void setupClickListeners() {
        btnLogin.setOnClickListener(v -> handleLogin());
        ivPasswordToggle.setOnClickListener(v -> togglePasswordVisibility());
    }

    private void handleLogin() {
        if (validateForm()) {
            performLogin();
        }
    }

    private boolean validateForm() {
        boolean isValid = true;
        
        // Email validation
        String email = etEmail.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email is required");
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please enter a valid email address");
            isValid = false;
        } else {
            etEmail.setError(null);
        }

        // Password validation
        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password is required");
            isValid = false;
        } else if (password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters");
            isValid = false;
        } else if (password.length() > 50) {
            etPassword.setError("Password is too long (max 50 characters)");
            isValid = false;
        } else {
            etPassword.setError(null);
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
    
    private void startLogoAnimation() {
        // Set initial state - make logo invisible and positioned for animation
        logoContainer.setAlpha(0f);
        logoContainer.setScaleX(0.1f);
        logoContainer.setScaleY(0.1f);
        logoContainer.setRotation(180f);
        logoContainer.setTranslationY(-30f);
        
        // Set initial state for text views
        tvAppName.setAlpha(0f);
        tvAppName.setTranslationY(50f);
        tvAppName.setScaleX(0.8f);
        tvAppName.setScaleY(0.8f);
        
        tvTagline.setAlpha(0f);
        tvTagline.setTranslationY(30f);
        
        // Set initial state for login form
        loginFormContainer.setAlpha(0f);
        loginFormContainer.setTranslationY(50f);
        
        // Start the animation sequence
        animateAppName();
    }
    
    private void animateAppName() {
        // App name animation - fade in with scale and translation
        ObjectAnimator alpha = ObjectAnimator.ofFloat(tvAppName, "alpha", 0f, 1f);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(tvAppName, "translationY", 50f, 0f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(tvAppName, "scaleX", 0.8f, 1.0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(tvAppName, "scaleY", 0.8f, 1.0f);
        
        AnimatorSet appNameSet = new AnimatorSet();
        appNameSet.playTogether(alpha, translationY, scaleX, scaleY);
        appNameSet.setDuration(1200);
        appNameSet.setInterpolator(new AccelerateDecelerateInterpolator());
        appNameSet.setStartDelay(500);
        
        appNameSet.addListener(new android.animation.AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                animateTagline();
            }
        });
        
        appNameSet.start();
    }
    
    private void animateTagline() {
        // Tagline animation - fade in with translation
        ObjectAnimator alpha = ObjectAnimator.ofFloat(tvTagline, "alpha", 0f, 1f);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(tvTagline, "translationY", 30f, 0f);
        
        AnimatorSet taglineSet = new AnimatorSet();
        taglineSet.playTogether(alpha, translationY);
        taglineSet.setDuration(1000);
        taglineSet.setInterpolator(new AccelerateDecelerateInterpolator());
        taglineSet.setStartDelay(200);
        
        taglineSet.addListener(new android.animation.AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                animateLogoContainer();
            }
        });
        
        taglineSet.start();
    }
    
    private void animateLogoContainer() {
        // Logo container animation - rotation and scale with glow effect
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(logoContainer, "scaleX", 0.1f, 1.2f, 1.0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(logoContainer, "scaleY", 0.1f, 1.2f, 1.0f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(logoContainer, "alpha", 0f, 1f);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(logoContainer, "rotation", 180f, 0f);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(logoContainer, "translationY", -30f, 0f);
        
        AnimatorSet logoSet = new AnimatorSet();
        logoSet.playTogether(scaleX, scaleY, alpha, rotation, translationY);
        logoSet.setDuration(1200);
        logoSet.setInterpolator(new AnticipateOvershootInterpolator());
        logoSet.setStartDelay(300);
        
        logoSet.addListener(new android.animation.AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                animateLoginForm();
            }
        });
        
        logoSet.start();
    }
    
    private void animateLoginForm() {
        // Login form animation - fade in with slide up
        ObjectAnimator alpha = ObjectAnimator.ofFloat(loginFormContainer, "alpha", 0f, 1f);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(loginFormContainer, "translationY", 50f, 0f);
        
        AnimatorSet formSet = new AnimatorSet();
        formSet.playTogether(alpha, translationY);
        formSet.setDuration(800);
        formSet.setInterpolator(new AccelerateDecelerateInterpolator());
        formSet.setStartDelay(200);
        
        formSet.addListener(new android.animation.AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                startContinuousGlow();
            }
        });
        
        formSet.start();
    }
    
    private void startContinuousGlow() {
        // Create a subtle pulsing glow effect
        ObjectAnimator glowScaleX = ObjectAnimator.ofFloat(logoContainer, "scaleX", 1.0f, 1.05f, 1.0f);
        ObjectAnimator glowScaleY = ObjectAnimator.ofFloat(logoContainer, "scaleY", 1.0f, 1.05f, 1.0f);
        ObjectAnimator glowAlpha = ObjectAnimator.ofFloat(logoContainer, "alpha", 1.0f, 0.8f, 1.0f);
        
        AnimatorSet glowSet = new AnimatorSet();
        glowSet.playTogether(glowScaleX, glowScaleY, glowAlpha);
        glowSet.setDuration(2000);
        glowSet.setInterpolator(new LinearInterpolator());
        glowSet.setStartDelay(1000);
        
        glowSet.addListener(new android.animation.AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                // Restart the glow effect for continuous animation
                startContinuousGlow();
            }
        });
        
        glowSet.start();
    }
    
    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Hide password
            etPassword.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
            ivPasswordToggle.setImageResource(R.drawable.ic_visibility_off);
            isPasswordVisible = false;
        } else {
            // Show password
            etPassword.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            ivPasswordToggle.setImageResource(R.drawable.ic_visibility);
            isPasswordVisible = true;
        }
        
        // Move cursor to end
        etPassword.setSelection(etPassword.getText().length());
    }
}
