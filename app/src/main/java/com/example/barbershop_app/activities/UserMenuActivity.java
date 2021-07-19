package com.example.barbershop_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.barbershop_app.R;
import com.google.firebase.auth.FirebaseAuth;

public class UserMenuActivity extends AppCompatActivity {

    Button bookAppointmentButton;
    Button viewBookedAppointment;
    Button logOutButton;
    String userObj;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        bookAppointmentButton = findViewById(R.id.buttonBookAppiontment);
        viewBookedAppointment = findViewById(R.id.buttonViewBookedApp);
        logOutButton = findViewById(R.id.buttonLogOutMainMenu);
        userObj = getIntent().getStringExtra("userObj");

    }

    @Override
    protected void onResume() {
        super.onResume();

        bookAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSchedulerActivity = new Intent(getApplicationContext(), SchedulerActivity.class);
                goToSchedulerActivity.putExtra("userObj", userObj);
                startActivity(goToSchedulerActivity);
            }
        });

        viewBookedAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Lifecycle: ", "LoggedInActivity onClick viewBookedAppointment");
                Intent userBookedAppsIntent = new Intent(getApplicationContext(), UserBookedAppsActivity.class);// go to Main Menu
                userBookedAppsIntent.putExtra("userObj", userObj);
                startActivity(userBookedAppsIntent);
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