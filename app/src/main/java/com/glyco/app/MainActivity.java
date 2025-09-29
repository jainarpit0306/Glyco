package com.glyco.app;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        initializeViews();
    }

    private void initializeViews() {
        // Get card references for click listeners
        MaterialCardView profileCard = findViewById(R.id.profileCard);
        MaterialCardView sloganCard = findViewById(R.id.sloganCard);
        MaterialCardView appointmentCard = findViewById(R.id.appointmentCard);
        MaterialCardView healthServicesCard = findViewById(R.id.healthServicesCard);
        MaterialCardView additionalServicesCard = findViewById(R.id.additionalServicesCard);
        MaterialCardView consultationCard = findViewById(R.id.consultationCard);
        MaterialCardView extendedServicesCard = findViewById(R.id.extendedServicesCard);
        LinearLayout newsRoomLayout = findViewById(R.id.newsRoomLayout);

        // Set click listeners for cards
        if (profileCard != null) {
            profileCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast("Profile information - Mr. VICKY SOLANKI 👤");
                }
            });
        }

        if (sloganCard != null) {
            sloganCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast("GLYCO - Treat to Target And Live Longer 🌿");
                }
            });
        }

        if (appointmentCard != null) {
            appointmentCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast("Your next appointment at GLYCO on 28-Mar-2023 📅");
                }
            });
        }

        if (healthServicesCard != null) {
            healthServicesCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast("Health Care Services - News, Knowledge, Diet 🏥");
                }
            });
        }

        if (additionalServicesCard != null) {
            additionalServicesCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast("Additional Health Services - Medication, Tracking, Emergency 💊");
                }
            });
        }

        if (consultationCard != null) {
            consultationCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast("Consultation and Health Record - Consultation, Reports, Glucose Diary 📊");
                }
            });
        }

        if (extendedServicesCard != null) {
            extendedServicesCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast("Extended Health Services - Lab Tests, Appointments, Health Goals 🎯");
                }
            });
        }

        if (newsRoomLayout != null) {
            newsRoomLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast("📰 News Room - Latest health updates, medical breakthroughs, and wellness tips! Stay informed with the latest in healthcare technology and research.");
                }
            });
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
