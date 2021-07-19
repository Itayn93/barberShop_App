package com.example.barbershop_app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barbershop_app.R;
import com.example.barbershop_app.classes.Appointment;
import com.example.barbershop_app.classes.JsonIO;
import com.example.barbershop_app.classes.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserBookedAppsActivity extends AppCompatActivity {

    //private DatabaseReference dbUsers;
    private DatabaseReference dbAppointments;
    TextView fullName;
    TextView date;
    TextView hour;
    User signedInUser = new User();
    String userObj;
    Appointment checkDBAppointment = new Appointment();
    Button rescheduleApp;
    Button cancelApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_booked_apps);
        Log.d("Lifecycle: ", "UserBookedAppsActivity onCreate ");
        //dbUsers = FirebaseDatabase.getInstance().getReference().child("users");
        dbAppointments = FirebaseDatabase.getInstance().getReference().child("Appointments");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Lifecycle: ", "UserBookedAppsActivity onStart ");
        fullName = findViewById(R.id.info_textFullName);
        date = findViewById(R.id.info_textDate);
        hour = findViewById(R.id.info_textHour);
        rescheduleApp = findViewById(R.id.buttonRescheduleApp);
        cancelApp = findViewById(R.id.buttonCancelApp);


        userObj = getIntent().getStringExtra("userObj");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Lifecycle: ", "UserBookedAppsActivity onResume ");

        try {
            signedInUser = JsonIO.JsonString_to_Object(userObj,User.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        dbAppointments.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //int appAvailable = 1;
                Log.d("Lifecycle: ", "UserBookedAppsActivity onDataChange ");
                for (DataSnapshot dsp : snapshot.getChildren()) { //enhanced loop
                    checkDBAppointment = dsp.getValue(Appointment.class);
                    String uid = dsp.getKey();
                    if (uid.equals(signedInUser.getId())) {
                        fullName.setText(signedInUser.getFullName());
                        date.setText(checkDBAppointment.getDate());//String.valueOf(checkDBAppointment.getDayOfMonth())+"/"+String.valueOf(checkDBAppointment.getMonth())+"/"+String.valueOf(checkDBAppointment.getYear()));
                        hour.setText(checkDBAppointment.getHour());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        rescheduleApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Lifecycle: ", "rescheduleApp onClick UserBookedAppsActivity");
                Intent schedulerIntent = new Intent(getApplicationContext(), SchedulerActivity.class);
                schedulerIntent.putExtra("userObj", userObj);
                startActivity(schedulerIntent);
            }
        });

        cancelApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Lifecycle: ", "cancelApp onClick UserBookedAppsActivity");
                signedInUser.setFullName(" YOU HAVE ");
                checkDBAppointment.setDate(" NO APPOINTMENTS");
                checkDBAppointment.setHour(" BOOKED ");
                fullName.setText(signedInUser.getFullName());
                date.setText(checkDBAppointment.getDate());
                hour.setText(checkDBAppointment.getHour());
                dbAppointments.child(signedInUser.getId()).removeValue();

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Lifecycle: ", "UserBookedAppsActivity onPause ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Lifecycle: ", "UserBookedAppsActivity onStop ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Lifecycle: ", "UserBookedAppsActivity onDestroy ");
    }
}