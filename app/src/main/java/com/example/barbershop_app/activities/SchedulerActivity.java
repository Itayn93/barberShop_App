package com.example.barbershop_app.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.ValueEventListener;

public class SchedulerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] hours = { "08:00", "08:30", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00",
                       "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30",
                       "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00"};

    //private FirebaseAuth mAuth;
    private DatabaseReference dbAppointments;
    CalendarView calendarView;
    Button bookButton;
    Appointment appointment = new Appointment();
    Appointment checkDBAppointment = new Appointment();
    Spinner hourPicker;
    User signedInUser = new User();
    String userObj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);

        //mAuth = FirebaseAuth.getInstance(); // Initialize Firebase Auth
        dbAppointments = FirebaseDatabase.getInstance().getReference().child("Appointments");// enable read/write to DB



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
                //int appointmentAvailable = readAppointmentFromDB(signedInUser.getId());
                //if (appointmentAvailable == 1){
                // check if appointment not booked already !
                Log.d("Lifecycle: ", "LoggedInActivity onClick SchedulerActivity");
                dbAppointments.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //int appAvailable = 1;
                        Log.d("Lifecycle: ", "LoggedInActivity onDataChange SchedulerActivity");
                        for (DataSnapshot dsp : snapshot.getChildren()) { //enhanced loop
                            checkDBAppointment = dsp.getValue(Appointment.class);
                            String uid = dsp.getKey();
                            if (!uid.equals(signedInUser.getId())) {
                                if (checkDBAppointment.getDayOfMonth() == appointment.getDayOfMonth() && checkDBAppointment.getHour().equals(appointment.getHour())  &&
                                        checkDBAppointment.getMonth() == appointment.getMonth() && checkDBAppointment.getYear() == appointment.getYear()) {
                                    //Log.d("Lifecycle: ", "LoggedInActivity onDataChange IF SchedulerActivity");
                                    Toast.makeText(SchedulerActivity.this, "Appointment unavailable ", Toast.LENGTH_LONG).show();
                                    break;
                                }
                                else { // if appointment is available
                                    //Log.d("Lifecycle: ", "LoggedInActivity onDataChange ELSE SchedulerActivity");
                                    writeNewAppointment(signedInUser.getId(),appointment);

                                    Toast.makeText(SchedulerActivity.this, "Appointment Booked !", Toast.LENGTH_LONG).show();

                                    Intent userBookedAppsIntent = new Intent(getApplicationContext(), UserBookedAppsActivity.class);// go to Main Menu
                                    userBookedAppsIntent.putExtra("userObj", userObj);
                                    startActivity(userBookedAppsIntent);

                                }


                            }
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

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

        dbAppointments.child(userId).setValue(appointment);
    }


  /*  public int readAppointmentFromDB(String userId) {



      dbAppointments.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
              int appAvailable = 1;
              for (DataSnapshot dsp : snapshot.getChildren()) {
                  checkDBAppointment = dsp.getValue(Appointment.class);
                  if (dbAppointments.getKey() != signedInUser.getId()){
                      if (checkDBAppointment.getDayOfMonth() == appointment.getDayOfMonth() && checkDBAppointment.getHour() == appointment.getHour() &&
                          checkDBAppointment.getMonth() == appointment.getMonth() && checkDBAppointment.getYear() == appointment.getYear()) {

                          appAvailable = 0;
                          break;
                      }


                  }
              }


          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
      });

      return appAvailable;
      }*/


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