package com.example.barbershop_app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import com.example.barbershop_app.R;
import com.example.barbershop_app.classes.Appointment;
import com.example.barbershop_app.classes.CustomAdapterAdminBookedApps;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AdminBookedAppsActivity extends AppCompatActivity {

    ArrayList<Appointment> appointmentsList = new ArrayList<>();
    RecyclerView recyclerView;
    CustomAdapterAdminBookedApps customAdapterAdminBookedApps;
    private DatabaseReference dbAppointments;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Lifecycle: ", "AdminBookedAppsActivity onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_booked_apps);
        dbAppointments = FirebaseDatabase.getInstance().getReference().child("Appointments");// enable read/write to DB

    }

    @Override
    protected void onStart() {
        Log.d("Lifecycle: ", "AdminBookedAppsActivity onStart");
        super.onStart();

        recyclerView = findViewById(R.id.RecyclerViewBookedApps);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }

    @Override
    protected void onResume() {
        Log.d("Lifecycle: ", "AdminBookedAppsActivity onResume");
        super.onResume();

       dbAppointments.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for(DataSnapshot dsp : dataSnapshot.getChildren()){

                   Appointment appointment = dsp.getValue(Appointment.class);
                   appointmentsList.add(appointment);
               }
               customAdapterAdminBookedApps = new CustomAdapterAdminBookedApps(appointmentsList);
               recyclerView.setAdapter(customAdapterAdminBookedApps);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
    }

    @Override
    protected void onPause() {
        Log.d("Lifecycle: ", "AdminBookedAppsActivity onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("Lifecycle: ", "AdminBookedAppsActivity onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("Lifecycle: ", "AdminBookedAppsActivity onDestroy");
        super.onDestroy();
    }
}