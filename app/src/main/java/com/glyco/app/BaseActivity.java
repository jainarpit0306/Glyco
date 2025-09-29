package com.glyco.app;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public abstract class BaseActivity extends AppCompatActivity {
    
    protected ImageView btnMenu;
    protected ImageView btnChat;
    protected ImageView btnNotifications;
    protected TextView notificationBadge;
    protected DrawerLayout drawerLayout;
    protected ImageView btnCloseDrawer;
    protected LinearLayout menuViewProfile;
    protected LinearLayout menuAppointmentHistory;
    protected LinearLayout menuLanguageChange;
    protected LinearLayout menuAboutUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Set the layout for the activity
        setContentView(getLayoutResourceId());
        
        // Initialize topbar components
        initializeTopbar();
        
        // Setup topbar click listeners
        setupTopbarListeners();
        
        // Initialize activity-specific components
        initializeActivity();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // Ensure drawer is closed when activity resumes
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    
    /**
     * Get the layout resource ID for the activity
     * Must be implemented by subclasses
     */
    protected abstract int getLayoutResourceId();
    
    /**
     * Initialize activity-specific components
     * Can be overridden by subclasses
     */
    protected void initializeActivity() {
        // Default implementation - can be overridden
    }
    
    private void initializeTopbar() {
        btnMenu = findViewById(R.id.btn_menu);
        btnChat = findViewById(R.id.btn_chat);
        btnNotifications = findViewById(R.id.btn_notifications);
        notificationBadge = findViewById(R.id.notification_badge);
        drawerLayout = findViewById(R.id.drawer_layout);
        
        // Initialize drawer components
        btnCloseDrawer = findViewById(R.id.btn_close_drawer);
        menuViewProfile = findViewById(R.id.menu_view_profile);
        menuAppointmentHistory = findViewById(R.id.menu_appointment_history);
        menuLanguageChange = findViewById(R.id.menu_language_change);
        menuAboutUs = findViewById(R.id.menu_about_us);
        
        // Set initial notification badge visibility
        updateNotificationBadge(3); // Default to 3 notifications
        
        // Ensure drawer is closed by default
        if (drawerLayout != null && !drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    
    private void setupTopbarListeners() {
        if (btnMenu != null) {
            btnMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMenuClicked();
                }
            });
        }
        
        if (btnChat != null) {
            btnChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onChatClicked();
                }
            });
        }
        
        if (btnNotifications != null) {
            btnNotifications.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNotificationsClicked();
                }
            });
        }
        
        // Setup drawer listeners
        setupDrawerListeners();
    }
    
    private void setupDrawerListeners() {
        if (btnCloseDrawer != null) {
            btnCloseDrawer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    closeDrawer();
                }
            });
        }
        
        if (menuViewProfile != null) {
            menuViewProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onViewProfileClicked();
                    closeDrawer();
                }
            });
        }
        
        if (menuAppointmentHistory != null) {
            menuAppointmentHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAppointmentHistoryClicked();
                    closeDrawer();
                }
            });
        }
        
        if (menuLanguageChange != null) {
            menuLanguageChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onLanguageChangeClicked();
                    closeDrawer();
                }
            });
        }
        
        if (menuAboutUs != null) {
            menuAboutUs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAboutUsClicked();
                    closeDrawer();
                }
            });
        }
    }
    
    /**
     * Handle menu button click
     * Can be overridden by subclasses for custom behavior
     */
    protected void onMenuClicked() {
        // Default implementation - open drawer
        if (drawerLayout != null) {
            drawerLayout.openDrawer(GravityCompat.START);
        } else {
            showToast("Menu clicked - Navigation drawer not available");
        }
    }
    
    /**
     * Handle chat button click
     * Can be overridden by subclasses for custom behavior
     */
    protected void onChatClicked() {
        showToast("Chat feature coming soon! 💬");
    }
    
    /**
     * Handle notifications button click
     * Can be overridden by subclasses for custom behavior
     */
    protected void onNotificationsClicked() {
        showToast("Notifications - You have " + getNotificationCount() + " new notifications 🔔");
        // Clear notifications
        updateNotificationBadge(0);
    }
    
    /**
     * Update notification badge count and visibility
     */
    protected void updateNotificationBadge(int count) {
        if (notificationBadge != null) {
            if (count > 0) {
                notificationBadge.setVisibility(View.VISIBLE);
                notificationBadge.setText(String.valueOf(count));
            } else {
                notificationBadge.setVisibility(View.GONE);
            }
        }
    }
    
    /**
     * Get current notification count
     */
    protected int getNotificationCount() {
        if (notificationBadge != null && notificationBadge.getVisibility() == View.VISIBLE) {
            try {
                return Integer.parseInt(notificationBadge.getText().toString());
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }
    
    /**
     * Close the navigation drawer
     */
    protected void closeDrawer() {
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    
    /**
     * Handle drawer menu item clicks
     * Can be overridden by subclasses for custom behavior
     */
    protected void onViewProfileClicked() {
        showToast("View Profile - Feature coming soon! 👤");
    }
    
    protected void onAppointmentHistoryClicked() {
        showToast("Appointment History - Feature coming soon! 📅");
    }
    
    protected void onLanguageChangeClicked() {
        showToast("Language Change - Feature coming soon! 🌐");
    }
    
    protected void onAboutUsClicked() {
        showToast("About Us - Glyco Health App v2.9 📱");
    }
    
    /**
     * Show a toast message
     */
    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
