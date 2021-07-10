package com.example.barbershop_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.barbershop_app.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainMenuActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button bookAppointmentButton;
    Button cancelAppointmentButton;
    Button rescheduleAppointmentButton;
    Button logOutButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mAuth = FirebaseAuth.getInstance(); // Initialize Firebase Auth
    }

    @Override
    protected void onStart() {
        super.onStart();
        bookAppointmentButton = findViewById(R.id.buttonBookAppiontment);
        cancelAppointmentButton = findViewById(R.id.buttonCancelAppointment);
        rescheduleAppointmentButton = findViewById(R.id.buttonRescheduleAppointment);
        logOutButton = findViewById(R.id.buttonLogOutMainMenu);

    }

    @Override
    protected void onResume() {
        super.onResume();

        bookAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSchedulerActivity = new Intent(getApplicationContext(), SchedulerActivity.class);// go to Main Menu
                startActivity(goToSchedulerActivity);
            }
        });

        cancelAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        rescheduleAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnToMainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);// go to Main Menu
                startActivity(returnToMainActivityIntent);
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