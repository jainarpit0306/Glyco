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
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textview.MaterialTextView;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 5000; // 5 seconds total
    private static final int BACKGROUND_ANIMATION_DURATION = 2000;
    private static final int LOGO_ANIMATION_DURATION = 1200;
    private static final int TEXT_ANIMATION_DURATION = 1000;
    private static final int PROGRESS_ANIMATION_DURATION = 800;
    private static final int PARTICLE_ANIMATION_DURATION = 3000;

    private View backgroundParticles;
    private FrameLayout particlesContainer;
    private View logoGlow;
    private MaterialCardView logoContainer;
    private MaterialTextView appName;
    private MaterialTextView tagline;
    private CircularProgressIndicator progressIndicator;
    private MaterialTextView versionText;
    
    // Particle views
    private View[] particles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        
        initializeViews();
        startAnimationSequence();
    }

    private void initializeViews() {
        backgroundParticles = findViewById(R.id.background_particles);
        particlesContainer = findViewById(R.id.particles_container);
        logoGlow = findViewById(R.id.logo_glow);
        logoContainer = findViewById(R.id.logo_container);
        appName = findViewById(R.id.app_name);
        tagline = findViewById(R.id.tagline);
        progressIndicator = findViewById(R.id.progress_indicator);
        versionText = findViewById(R.id.version_text);
        
        // Initialize particle views
        particles = new View[6];
        particles[0] = findViewById(R.id.particle_1);
        particles[1] = findViewById(R.id.particle_2);
        particles[2] = findViewById(R.id.particle_3);
        particles[3] = findViewById(R.id.particle_4);
        particles[4] = findViewById(R.id.particle_5);
        particles[5] = findViewById(R.id.particle_6);
    }

    private void startAnimationSequence() {
        // Start background animation immediately
        animateBackground();
        
        // Start particle animations
        new Handler(Looper.getMainLooper()).postDelayed(this::animateParticles, 300);
        
        // Start logo glow animation
        new Handler(Looper.getMainLooper()).postDelayed(this::animateLogoGlow, 500);
        
        // Start logo animation after a short delay
        new Handler(Looper.getMainLooper()).postDelayed(this::animateLogo, 800);
        
        // Start text animations after logo starts
        new Handler(Looper.getMainLooper()).postDelayed(this::animateText, 1400);
        
        // Start progress indicator animation
        new Handler(Looper.getMainLooper()).postDelayed(this::animateProgress, 1800);
        
        // Start version text animation
        new Handler(Looper.getMainLooper()).postDelayed(this::animateVersion, 2200);
        
        // Navigate to main activity after all animations
        new Handler(Looper.getMainLooper()).postDelayed(this::navigateToMainActivity, SPLASH_DELAY);
    }

    private void animateBackground() {
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(backgroundParticles, "alpha", 0f, 0.6f);
        fadeIn.setDuration(BACKGROUND_ANIMATION_DURATION);
        fadeIn.setInterpolator(new AccelerateDecelerateInterpolator());
        fadeIn.start();
    }
    
    private void animateParticles() {
        // Show particles container
        ObjectAnimator containerFadeIn = ObjectAnimator.ofFloat(particlesContainer, "alpha", 0f, 1f);
        containerFadeIn.setDuration(500);
        containerFadeIn.start();
        
        // Animate individual particles with staggered delays
        for (int i = 0; i < particles.length; i++) {
            final int index = i;
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                animateParticle(particles[index], index);
            }, i * 200);
        }
    }
    
    private void animateParticle(View particle, int index) {
        // Random floating animation
        ObjectAnimator floatY = ObjectAnimator.ofFloat(particle, "translationY", 
            (index % 2 == 0) ? 100f : -100f, (index % 2 == 0) ? -100f : 100f);
        ObjectAnimator floatX = ObjectAnimator.ofFloat(particle, "translationX", 
            (index % 3 == 0) ? 50f : -50f, (index % 3 == 0) ? -50f : 50f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(particle, "alpha", 0f, 0.8f, 0f);
        ObjectAnimator scale = ObjectAnimator.ofFloat(particle, "scaleX", 0.5f, 1.2f, 0.5f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(particle, "scaleY", 0.5f, 1.2f, 0.5f);
        
        // Set repeat properties on individual animators
        floatY.setRepeatCount(ValueAnimator.INFINITE);
        floatY.setRepeatMode(ValueAnimator.REVERSE);
        floatX.setRepeatCount(ValueAnimator.INFINITE);
        floatX.setRepeatMode(ValueAnimator.REVERSE);
        alpha.setRepeatCount(ValueAnimator.INFINITE);
        alpha.setRepeatMode(ValueAnimator.REVERSE);
        scale.setRepeatCount(ValueAnimator.INFINITE);
        scale.setRepeatMode(ValueAnimator.REVERSE);
        scaleY.setRepeatCount(ValueAnimator.INFINITE);
        scaleY.setRepeatMode(ValueAnimator.REVERSE);
        
        AnimatorSet particleSet = new AnimatorSet();
        particleSet.playTogether(floatY, floatX, alpha, scale, scaleY);
        particleSet.setDuration(PARTICLE_ANIMATION_DURATION);
        particleSet.setInterpolator(new LinearInterpolator());
        particleSet.start();
    }
    
    private void animateLogoGlow() {
        // Glow effect animation
        ObjectAnimator glowScaleX = ObjectAnimator.ofFloat(logoGlow, "scaleX", 0f, 1f);
        ObjectAnimator glowScaleY = ObjectAnimator.ofFloat(logoGlow, "scaleY", 0f, 1f);
        ObjectAnimator glowAlpha = ObjectAnimator.ofFloat(logoGlow, "alpha", 0f, 0.6f);
        
        AnimatorSet glowSet = new AnimatorSet();
        glowSet.playTogether(glowScaleX, glowScaleY, glowAlpha);
        glowSet.setDuration(LOGO_ANIMATION_DURATION);
        glowSet.setInterpolator(new OvershootInterpolator(1.5f));
        glowSet.start();
        
        // Start pulsing glow effect
        new Handler(Looper.getMainLooper()).postDelayed(this::startGlowPulse, LOGO_ANIMATION_DURATION);
    }
    
    private void startGlowPulse() {
        ObjectAnimator pulse = ObjectAnimator.ofFloat(logoGlow, "alpha", 0.6f, 0.2f, 0.6f);
        pulse.setDuration(2000);
        pulse.setRepeatCount(ValueAnimator.INFINITE);
        pulse.setRepeatMode(ValueAnimator.REVERSE);
        pulse.setInterpolator(new AccelerateDecelerateInterpolator());
        pulse.start();
    }

    private void animateLogo() {
        // Enhanced scale animation with bounce
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(logoContainer, "scaleX", 0f, 1.1f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(logoContainer, "scaleY", 0f, 1.1f, 1f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(logoContainer, "alpha", 0f, 1f);
        
        AnimatorSet logoAnimatorSet = new AnimatorSet();
        logoAnimatorSet.playTogether(scaleX, scaleY, alpha);
        logoAnimatorSet.setDuration(LOGO_ANIMATION_DURATION);
        logoAnimatorSet.setInterpolator(new BounceInterpolator());
        
        // Add rotation and elevation effects
        ObjectAnimator rotation = ObjectAnimator.ofFloat(logoContainer, "rotation", -15f, 5f, 0f);
        rotation.setDuration(LOGO_ANIMATION_DURATION);
        rotation.setInterpolator(new DecelerateInterpolator());
        
        // Add a subtle floating effect
        ObjectAnimator floatY = ObjectAnimator.ofFloat(logoContainer, "translationY", 20f, -10f, 0f);
        floatY.setDuration(LOGO_ANIMATION_DURATION);
        floatY.setInterpolator(new AccelerateDecelerateInterpolator());
        
        AnimatorSet combinedSet = new AnimatorSet();
        combinedSet.playTogether(logoAnimatorSet, rotation, floatY);
        combinedSet.start();
        
        // Start continuous floating animation after initial animation
        new Handler(Looper.getMainLooper()).postDelayed(this::startLogoFloat, LOGO_ANIMATION_DURATION);
    }
    
    private void startLogoFloat() {
        ObjectAnimator floatAnimation = ObjectAnimator.ofFloat(logoContainer, "translationY", 0f, -8f, 0f);
        floatAnimation.setDuration(3000);
        floatAnimation.setRepeatCount(ValueAnimator.INFINITE);
        floatAnimation.setRepeatMode(ValueAnimator.REVERSE);
        floatAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        floatAnimation.start();
    }

    private void animateText() {
        // Enhanced app name animation with typewriter effect
        startTypewriterEffect(appName, "GLYCO", 0);
        
        // Tagline animation with delay (after app name typewriter effect completes)
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            ObjectAnimator taglineAlpha = ObjectAnimator.ofFloat(tagline, "alpha", 0f, 1f);
            ObjectAnimator taglineTranslation = ObjectAnimator.ofFloat(tagline, "translationY", 10f, 0f);
            ObjectAnimator taglineScale = ObjectAnimator.ofFloat(tagline, "scaleX", 0.8f, 1f);
            ObjectAnimator taglineScaleY = ObjectAnimator.ofFloat(tagline, "scaleY", 0.8f, 1f);
            
            AnimatorSet taglineSet = new AnimatorSet();
            taglineSet.playTogether(taglineAlpha, taglineTranslation, taglineScale, taglineScaleY);
            taglineSet.setDuration(TEXT_ANIMATION_DURATION);
            taglineSet.setInterpolator(new OvershootInterpolator(1.1f));
            taglineSet.start();
        }, 1500); // Further increased delay to ensure complete separation
    }
    
    private void startTypewriterEffect(TextView textView, String text, int index) {
        if (index < text.length()) {
            String currentText = text.substring(0, index + 1);
            textView.setText(currentText);
            
            // Add a subtle scale effect for each character
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(textView, "scaleX", 1.1f, 1f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(textView, "scaleY", 1.1f, 1f);
            ObjectAnimator alpha = ObjectAnimator.ofFloat(textView, "alpha", 0f, 1f);
            
            AnimatorSet charSet = new AnimatorSet();
            charSet.playTogether(scaleX, scaleY, alpha);
            charSet.setDuration(150);
            charSet.setInterpolator(new OvershootInterpolator(1.2f));
            charSet.start();
            
            // Continue with next character
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                startTypewriterEffect(textView, text, index + 1);
            }, 150); // Faster typewriter effect
        } else {
            // Add final flourish animation
            ObjectAnimator finalScale = ObjectAnimator.ofFloat(textView, "scaleX", 1f, 1.05f, 1f);
            ObjectAnimator finalScaleY = ObjectAnimator.ofFloat(textView, "scaleY", 1f, 1.05f, 1f);
            
            AnimatorSet finalSet = new AnimatorSet();
            finalSet.playTogether(finalScale, finalScaleY);
            finalSet.setDuration(300);
            finalSet.setInterpolator(new BounceInterpolator());
            finalSet.start();
        }
    }

    private void animateProgress() {
        // Enhanced progress indicator animation
        ObjectAnimator progressAlpha = ObjectAnimator.ofFloat(progressIndicator, "alpha", 0f, 1f);
        ObjectAnimator progressScaleX = ObjectAnimator.ofFloat(progressIndicator, "scaleX", 0.3f, 1.1f, 1f);
        ObjectAnimator progressScaleY = ObjectAnimator.ofFloat(progressIndicator, "scaleY", 0.3f, 1.1f, 1f);
        ObjectAnimator progressRotation = ObjectAnimator.ofFloat(progressIndicator, "rotation", -180f, 0f);
        
        AnimatorSet progressSet = new AnimatorSet();
        progressSet.playTogether(progressAlpha, progressScaleX, progressScaleY, progressRotation);
        progressSet.setDuration(PROGRESS_ANIMATION_DURATION);
        progressSet.setInterpolator(new BounceInterpolator());
        progressSet.start();
        
        // Start enhanced pulsing animation after initial animation
        new Handler(Looper.getMainLooper()).postDelayed(this::startProgressPulse, PROGRESS_ANIMATION_DURATION);
    }

    private void startProgressPulse() {
        // Enhanced pulsing with scale and rotation
        ObjectAnimator pulseAlpha = ObjectAnimator.ofFloat(progressIndicator, "alpha", 1f, 0.4f, 1f);
        ObjectAnimator pulseScale = ObjectAnimator.ofFloat(progressIndicator, "scaleX", 1f, 1.1f, 1f);
        ObjectAnimator pulseScaleY = ObjectAnimator.ofFloat(progressIndicator, "scaleY", 1f, 1.1f, 1f);
        
        // Set repeat properties on individual animators
        pulseAlpha.setRepeatCount(ValueAnimator.INFINITE);
        pulseAlpha.setRepeatMode(ValueAnimator.REVERSE);
        pulseScale.setRepeatCount(ValueAnimator.INFINITE);
        pulseScale.setRepeatMode(ValueAnimator.REVERSE);
        pulseScaleY.setRepeatCount(ValueAnimator.INFINITE);
        pulseScaleY.setRepeatMode(ValueAnimator.REVERSE);
        
        AnimatorSet pulseSet = new AnimatorSet();
        pulseSet.playTogether(pulseAlpha, pulseScale, pulseScaleY);
        pulseSet.setDuration(1500);
        pulseSet.setInterpolator(new AccelerateDecelerateInterpolator());
        pulseSet.start();
    }

    private void animateVersion() {
        // Enhanced version text animation
        ObjectAnimator versionAlpha = ObjectAnimator.ofFloat(versionText, "alpha", 0f, 1f);
        ObjectAnimator versionTranslation = ObjectAnimator.ofFloat(versionText, "translationY", 30f, 0f);
        ObjectAnimator versionScale = ObjectAnimator.ofFloat(versionText, "scaleX", 0.8f, 1f);
        ObjectAnimator versionScaleY = ObjectAnimator.ofFloat(versionText, "scaleY", 0.8f, 1f);
        
        AnimatorSet versionSet = new AnimatorSet();
        versionSet.playTogether(versionAlpha, versionTranslation, versionScale, versionScaleY);
        versionSet.setDuration(800);
        versionSet.setInterpolator(new OvershootInterpolator(1.1f));
        versionSet.start();
        
        // Start breathing effect for version text
        new Handler(Looper.getMainLooper()).postDelayed(this::startVersionBreathing, 800);
    }
    
    private void startVersionBreathing() {
        ObjectAnimator breathing = ObjectAnimator.ofFloat(versionText, "alpha", 1f, 0.6f, 1f);
        breathing.setDuration(3000);
        breathing.setRepeatCount(ValueAnimator.INFINITE);
        breathing.setRepeatMode(ValueAnimator.REVERSE);
        breathing.setInterpolator(new AccelerateDecelerateInterpolator());
        breathing.start();
    }
    
    private void navigateToMainActivity() {
        // Enhanced exit animation with scale and rotation
        View mainContainer = findViewById(R.id.main_container);
        
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(mainContainer, "alpha", 1f, 0f);
        ObjectAnimator scaleOut = ObjectAnimator.ofFloat(mainContainer, "scaleX", 1f, 0.8f);
        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(mainContainer, "scaleY", 1f, 0.8f);
        ObjectAnimator rotateOut = ObjectAnimator.ofFloat(mainContainer, "rotation", 0f, 5f);
        
        AnimatorSet exitSet = new AnimatorSet();
        exitSet.playTogether(fadeOut, scaleOut, scaleOutY, rotateOut);
        exitSet.setDuration(800);
        exitSet.setInterpolator(new AccelerateDecelerateInterpolator());
        exitSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Close splash activity
            }
        });
        exitSet.start();
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        // Prevent back button from returning to splash screen
        finish();
    }
}
