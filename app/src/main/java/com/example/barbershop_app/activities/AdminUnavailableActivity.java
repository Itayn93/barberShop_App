package com.example.barbershop_app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

//import android.app.DatePickerDialog;
//import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.barbershop_app.R;

import java.util.Calendar;

import com.example.barbershop_app.classes.Appointment;
import com.example.barbershop_app.classes.FirebaseHandler;
import com.example.barbershop_app.classes.UnavailableDate;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

public class AdminUnavailableActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    Button enterDate;
    Button confirmUnavailability;
    Button logOutButton;
    Spinner startHour;
    Spinner endHour;
    DatePickerDialog datePickerDialog ;
   // TimePickerDialog timePickerDialog ;
    Calendar now = Calendar.getInstance();
    UnavailableDate unavailableDate = new UnavailableDate();
    private DatabaseReference dbUnavailableDates;
    private DatabaseReference dbDateID;
    int countUnavailableDates = 0;
    String[] hourList = { "08:00", "08:30", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00",
                          "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30",
                          "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00"};

    int dateID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_unavailable);
    }

    @Override
    protected void onStart() {
        super.onStart();

        enterDate = findViewById(R.id.buttonEnterDate);
        logOutButton = findViewById(R.id.buttonAdminLogOut);
        confirmUnavailability = findViewById(R.id.buttonConfimUnavailability);
        startHour = findViewById(R.id.spinnerStartHour);
        endHour = findViewById(R.id.spinnerEndHour);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, hourList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        startHour.setAdapter(adapter);
        startHour.setOnItemSelectedListener(this);

        endHour.setAdapter(adapter);
        endHour.setOnItemSelectedListener(this);


        //enterTime = findViewById(R.id.buttonEnterTime);
        dbUnavailableDates = FirebaseDatabase.getInstance().getReference().child("Unavailable Dates");
        dbDateID = FirebaseDatabase.getInstance().getReference().child("DateID");


    }

    @Override
    protected void onResume() {
        super.onResume();

        // getting unavailable dates counter from db
        dbDateID.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Task<DataSnapshot> dsp  = dbDateID.get();

                String dateId = snapshot.getValue().toString();
                dateID = Integer.parseInt(dateId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        enterDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datePickerDialog = DatePickerDialog.newInstance(AdminUnavailableActivity.this, now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setTitle("Choose A Date");
                datePickerDialog.show(getSupportFragmentManager(),"DatePicker");
            }
        });

        confirmUnavailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbUnavailableDates.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        dbUnavailableDates.child(String.valueOf(dateID)).setValue(unavailableDate);// Writing the unavailable date to DB
                        dbDateID.setValue(++dateID); // update counter dateID
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Lifecycle: ", "UserMenuActivity onClick logOutButton");
                Intent returnToMainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);// go to Main Menu
                startActivity(returnToMainActivityIntent);
            }
        });

    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear+1) + "/" + String.valueOf(year);
        unavailableDate.setDate(date);
    }

    /*public void setUnavailableDate( int year, int monthOfYear, int dayOfMonth){
        String date = String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear+1) + "/" + String.valueOf(year);
        //UnavailableDate

    }*/

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner startHour = (Spinner)parent;
        Spinner endHour = (Spinner)parent;
        String hourSelected = parent.getItemAtPosition(position).toString();
        if(startHour.getId() == R.id.spinnerStartHour)
        {
            unavailableDate.setStartHour(hourSelected);
        }
        if(endHour.getId() == R.id.spinnerEndHour)
        {
            unavailableDate.setEndHour(hourSelected);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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