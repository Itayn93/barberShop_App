package com.example.barbershop_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.barbershop_app.R;

public class AdminMenuActivity extends AppCompatActivity {

    Button viewBookedAppointments;
    Button enterUnavailableDates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
    }

    @Override
    protected void onStart() {
        super.onStart();

        viewBookedAppointments = findViewById(R.id.buttonViewBookedApps);
        enterUnavailableDates = findViewById(R.id.buttonEnterUnavailavbleDates);
    }

    @Override
    protected void onResume() {
        super.onResume();

        viewBookedAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent adminBookedAppsActivityIntent = new Intent(getApplicationContext(), AdminBookedAppsActivity.class);
                startActivity(adminBookedAppsActivityIntent);
            }
        });

        enterUnavailableDates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }




    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}