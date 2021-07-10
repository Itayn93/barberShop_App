package com.example.barbershop_app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.barbershop_app.R;
import com.example.barbershop_app.classes.Appointment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SchedulerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] hours = { "08:00", "08:30", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00" };
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    CalendarView calendarView;
    Button bookButton;
    Appointment appointment;
    Spinner hourPicker;


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

        hourPicker = findViewById(R.id.spinnerHourPicker);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, hours);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hourPicker.setAdapter(adapter);
        hourPicker.setOnItemSelectedListener(this);
        // hourSelect.setIs24HourView(true);

    }

    @Override
    protected void onResume() {
        super.onResume();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                //appointment.setYear(year);
               // appointment.setMonth(month);
               // appointment.setDayOfMonth(dayOfMonth);

            }
        });

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // if date is available (get data from appointment db)
                Toast.makeText(SchedulerActivity.this, "Appointment Booked !", Toast.LENGTH_LONG).show();
                // else
                Toast.makeText(SchedulerActivity.this, "Appointment unavailable ", Toast.LENGTH_LONG).show();
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
        //Appointment appointment = new Appointment(year,month,dayOfMonth,hour);

        mDatabase.child("users").child(userId).setValue(appointment);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), "Selected User: "+hours[position] ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}