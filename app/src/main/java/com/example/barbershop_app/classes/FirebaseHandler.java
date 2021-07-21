package com.example.barbershop_app.classes;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseHandler {

    DatabaseReference dbUsers = FirebaseDatabase.getInstance().getReference("users");
    DatabaseReference dbAppointments = FirebaseDatabase.getInstance().getReference("Appointments");
    ArrayList<User> usersList = new ArrayList<User>();
    ArrayList<Appointment> appointmentsList = new ArrayList<Appointment>();


    public ArrayList<User> getUsersList(){

        dbUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot userSnapshot: datasnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    usersList.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //throw databaseError.toException();
            }
        });

        return usersList;
    }

    public ArrayList<Appointment> getAppointmentsList(){

        dbAppointments.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot appointmentSnapshot: datasnapshot.getChildren()) {
                    Appointment appointment = appointmentSnapshot.getValue(Appointment.class);
                    appointmentsList.add(appointment);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //throw databaseError.toException();
            }
        });

        return appointmentsList;

    }
}
