package com.example.barbershop_app.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.barbershop_app.classes.JsonIO;
import com.example.barbershop_app.classes.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SchedulerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] hours = { "08:00", "08:30", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00",
                       "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30",
                       "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00"};

    private FirebaseAuth mAuth;
    private DatabaseReference dbUsers;
    CalendarView calendarView;
    Button bookButton;
    Appointment appointment = new Appointment();
    Spinner hourPicker;
    User signedInUser = new User();
    String userObj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);

        mAuth = FirebaseAuth.getInstance(); // Initialize Firebase Auth
        dbUsers = FirebaseDatabase.getInstance().getReference();// enable read/write to DB



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

       userObj = getIntent().getStringExtra("userObj");


    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            signedInUser = JsonIO.JsonString_to_Object(userObj,User.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                appointment.setYear(year);
                appointment.setMonth(month+1);
                appointment.setDayOfMonth(dayOfMonth);
               // System.out.println(signedInUser.getId());

            }
        });

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                writeNewAppointment(signedInUser.getId(),appointment);
                // if date is available (get data from appointment db)
                Toast.makeText(SchedulerActivity.this, "Appointment Booked !", Toast.LENGTH_LONG).show();
                // else
                //Toast.makeText(SchedulerActivity.this, "Appointment unavailable ", Toast.LENGTH_LONG).show();
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


    public void writeNewAppointment(String userId, Appointment appointment) {
        //Appointment appointment = new Appointment(year,month,dayOfMonth,hour);

        dbUsers.child("Appointments").child(userId).setValue(appointment);
    }


    public void readAppointmentFromDB(String userId) {
        //Appointment appointment = new Appointment(year,month,dayOfMonth,hour);
        dbUsers.child("Users").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(SchedulerActivity.this, " Error getting data ", Toast.LENGTH_LONG).show();
                   // Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    //Log.d("firebase", String.valueOf(task.getResult().getValue()));

                }
            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String hourSelected = parent.getItemAtPosition(position).toString();
        appointment.setHour(hourSelected);
        //Toast.makeText(parent.getContext(), hourSelected,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}