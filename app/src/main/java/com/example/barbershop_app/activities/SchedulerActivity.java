package com.example.barbershop_app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.barbershop_app.R;
import com.example.barbershop_app.classes.Appointment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SchedulerActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    CalendarView calendarView;
    Button bookButton;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);

        mAuth = FirebaseAuth.getInstance(); // Initialize Firebase Auth
        mDatabase = FirebaseDatabase.getInstance().getReference();// enable read/write to DB

    }

    @Override
    protected void onStart() {
        super.onStart();

        calendarView = findViewById(R.id.calendarView);
        bookButton = findViewById(R.id.buttonBookScheduler);
        backButton = findViewById(R.id.buttonBackScheduler);
    }

    @Override
    protected void onResume() {
        super.onResume();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                // if date is available (get data from appointment db)
                Toast.makeText(SchedulerActivity.this, "Appointment Booked !", Toast.LENGTH_LONG).show();
                // else
                Toast.makeText(SchedulerActivity.this, "Appointment unavailable ", Toast.LENGTH_LONG).show();
            }
        });

        bookButton.setOnClickListener(new View.OnClickListener() {
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


    public void writeNewAppointment(String userId, int year ,int month, int dayOfMonth,int hour) {
        Appointment appointment = new Appointment(year,month,dayOfMonth,hour);

        mDatabase.child("users").child(userId).setValue(appointment);
    }
}