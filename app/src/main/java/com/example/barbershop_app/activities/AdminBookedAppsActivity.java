package com.example.barbershop_app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.barbershop_app.R;
import com.example.barbershop_app.classes.Appointment;
import com.example.barbershop_app.classes.FirebaseHandler;
import com.example.barbershop_app.classes.User;

import java.util.ArrayList;
import java.util.List;

public class AdminBookedAppsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    FirebaseHandler firebaseHandler = new FirebaseHandler();
    ArrayList<User> usersList = new ArrayList<User>();
    ArrayList<Appointment> appointmentsList = new ArrayList<Appointment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_booked_apps);
        usersList = firebaseHandler.getUsersList();
        appointmentsList = firebaseHandler.getAppointmentsList();

    }

    @Override
    protected void onStart() {
        super.onStart();
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