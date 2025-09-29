package com.glyco.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textview.MaterialTextView;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 4000; // 4 seconds total
    private static final int BACKGROUND_ANIMATION_DURATION = 1500;
    private static final int LOGO_ANIMATION_DURATION = 1000;
    private static final int TEXT_ANIMATION_DURATION = 800;
    private static final int PROGRESS_ANIMATION_DURATION = 600;

    private View backgroundGradient;
    private MaterialCardView logoContainer;
    private MaterialTextView appName;
    private MaterialTextView tagline;
    private CircularProgressIndicator progressIndicator;
    private MaterialTextView versionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        // Hide action bar for full screen experience
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        
        initializeViews();
        startAnimationSequence();
    }

    private void initializeViews() {
        backgroundGradient = findViewById(R.id.background_gradient);
        logoContainer = findViewById(R.id.logo_container);
        appName = findViewById(R.id.app_name);
        tagline = findViewById(R.id.tagline);
        progressIndicator = findViewById(R.id.progress_indicator);
        versionText = findViewById(R.id.version_text);
    }

    private void startAnimationSequence() {
        // Start background animation immediately
        animateBackground();
        
        // Start logo animation after a short delay
        new Handler(Looper.getMainLooper()).postDelayed(this::animateLogo, 200);
        
        // Start text animations after logo starts
        new Handler(Looper.getMainLooper()).postDelayed(this::animateText, 800);
        
        // Start progress indicator animation
        new Handler(Looper.getMainLooper()).postDelayed(this::animateProgress, 1200);
        
        // Start version text animation
        new Handler(Looper.getMainLooper()).postDelayed(this::animateVersion, 1600);
        
        // Navigate to main activity after all animations
        new Handler(Looper.getMainLooper()).postDelayed(this::navigateToMainActivity, SPLASH_DELAY);
    }

    private void animateBackground() {
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(backgroundGradient, "alpha", 0f, 1f);
        fadeIn.setDuration(BACKGROUND_ANIMATION_DURATION);
        fadeIn.setInterpolator(new AccelerateDecelerateInterpolator());
        fadeIn.start();
    }

    private void animateLogo() {
        // Scale animation
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(logoContainer, "scaleX", 0f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(logoContainer, "scaleY", 0f, 1f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(logoContainer, "alpha", 0f, 1f);
        
        AnimatorSet logoAnimatorSet = new AnimatorSet();
        logoAnimatorSet.playTogether(scaleX, scaleY, alpha);
        logoAnimatorSet.setDuration(LOGO_ANIMATION_DURATION);
        logoAnimatorSet.setInterpolator(new OvershootInterpolator(1.2f));
        
        // Add a subtle rotation effect
        ObjectAnimator rotation = ObjectAnimator.ofFloat(logoContainer, "rotation", -10f, 0f);
        rotation.setDuration(LOGO_ANIMATION_DURATION);
        rotation.setInterpolator(new DecelerateInterpolator());
        
        AnimatorSet combinedSet = new AnimatorSet();
        combinedSet.playTogether(logoAnimatorSet, rotation);
        combinedSet.start();
    }

    private void animateText() {
        // App name animation
        ObjectAnimator appNameAlpha = ObjectAnimator.ofFloat(appName, "alpha", 0f, 1f);
        ObjectAnimator appNameTranslation = ObjectAnimator.ofFloat(appName, "translationY", 50f, 0f);
        
        AnimatorSet appNameSet = new AnimatorSet();
        appNameSet.playTogether(appNameAlpha, appNameTranslation);
        appNameSet.setDuration(TEXT_ANIMATION_DURATION);
        appNameSet.setInterpolator(new DecelerateInterpolator());
        
        // Tagline animation (delayed)
        ObjectAnimator taglineAlpha = ObjectAnimator.ofFloat(tagline, "alpha", 0f, 1f);
        ObjectAnimator taglineTranslation = ObjectAnimator.ofFloat(tagline, "translationY", 50f, 0f);
        
        AnimatorSet taglineSet = new AnimatorSet();
        taglineSet.playTogether(taglineAlpha, taglineTranslation);
        taglineSet.setDuration(TEXT_ANIMATION_DURATION);
        taglineSet.setInterpolator(new DecelerateInterpolator());
        
        // Start app name first, then tagline
        appNameSet.start();
        new Handler(Looper.getMainLooper()).postDelayed(taglineSet::start, 200);
    }

    private void animateProgress() {
        ObjectAnimator progressAlpha = ObjectAnimator.ofFloat(progressIndicator, "alpha", 0f, 1f);
        progressAlpha.setDuration(PROGRESS_ANIMATION_DURATION);
        progressAlpha.setInterpolator(new DecelerateInterpolator());
        
        // Add a subtle scale animation
        ObjectAnimator progressScaleX = ObjectAnimator.ofFloat(progressIndicator, "scaleX", 0.5f, 1f);
        ObjectAnimator progressScaleY = ObjectAnimator.ofFloat(progressIndicator, "scaleY", 0.5f, 1f);
        
        AnimatorSet progressSet = new AnimatorSet();
        progressSet.playTogether(progressAlpha, progressScaleX, progressScaleY);
        progressSet.setDuration(PROGRESS_ANIMATION_DURATION);
        progressSet.setInterpolator(new OvershootInterpolator(0.8f));
        progressSet.start();
        
        // Start pulsing animation after initial animation
        new Handler(Looper.getMainLooper()).postDelayed(this::startProgressPulse, PROGRESS_ANIMATION_DURATION);
    }

    private void startProgressPulse() {
        ObjectAnimator pulse = ObjectAnimator.ofFloat(progressIndicator, "alpha", 1f, 0.3f, 1f);
        pulse.setDuration(1200);
        pulse.setRepeatCount(ValueAnimator.INFINITE);
        pulse.setRepeatMode(ValueAnimator.REVERSE);
        pulse.setInterpolator(new AccelerateDecelerateInterpolator());
        pulse.start();
    }

    private void animateVersion() {
        ObjectAnimator versionAlpha = ObjectAnimator.ofFloat(versionText, "alpha", 0f, 1f);
        ObjectAnimator versionTranslation = ObjectAnimator.ofFloat(versionText, "translationY", 30f, 0f);
        
        AnimatorSet versionSet = new AnimatorSet();
        versionSet.playTogether(versionAlpha, versionTranslation);
        versionSet.setDuration(600);
        versionSet.setInterpolator(new DecelerateInterpolator());
        versionSet.start();
    }
    
    private void navigateToMainActivity() {
        // Fade out animation before navigation
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(findViewById(R.id.main_container), "alpha", 1f, 0f);
        fadeOut.setDuration(500);
        fadeOut.setInterpolator(new AccelerateDecelerateInterpolator());
        fadeOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Close splash activity
            }
        });
        fadeOut.start();
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        // Prevent back button from returning to splash screen
        finish();
    }
}
