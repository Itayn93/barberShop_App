package com.example.barbershop_app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

    private DatabaseReference dbUsers;
    private DatabaseReference dbAppointments;
    TextView fullName;
    TextView date;
    TextView hour;
    User signedInUser = new User();
    String userObj;
    Appointment checkDBAppointment = new Appointment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_booked_apps);

        dbUsers = FirebaseDatabase.getInstance().getReference().child("users");
        dbAppointments = FirebaseDatabase.getInstance().getReference().child("Appointments");

    }

    @Override
    protected void onStart() {
        super.onStart();

        fullName = findViewById(R.id.info_textFullName);
        date = findViewById(R.id.info_textDate);
        hour = findViewById(R.id.info_textHour);

        userObj = getIntent().getStringExtra("userObj");
        try {
            signedInUser = JsonIO.JsonString_to_Object(userObj,User.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        fullName.setText(signedInUser.getFullName());

        dbAppointments.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //int appAvailable = 1;
                Log.d("Lifecycle: ", "LoggedInActivity onDataChange SchedulerActivity");
                for (DataSnapshot dsp : snapshot.getChildren()) { //enhanced loop
                    checkDBAppointment = dsp.getValue(Appointment.class);
                    String uid = dsp.getKey();
                    if (uid.equals(signedInUser.getId())) {
                        date.setText(String.valueOf(checkDBAppointment.getDayOfMonth())+"/"+String.valueOf(checkDBAppointment.getMonth())+"/"+String.valueOf(checkDBAppointment.getYear()));
                        hour.setText(checkDBAppointment.getHour());
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

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