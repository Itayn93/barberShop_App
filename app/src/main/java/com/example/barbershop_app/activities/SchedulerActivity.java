package com.example.barbershop_app.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.barbershop_app.R;
import com.example.barbershop_app.classes.Appointment;
import com.example.barbershop_app.classes.JsonIO;
import com.example.barbershop_app.classes.UnavailableDate;
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

import org.joda.time.DateTime;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.date.MonthAdapter;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

public class SchedulerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {//} , TimePickerDialog.OnTimeSetListener,  {

    private DatabaseReference dbAppointments;
    private DatabaseReference dbUnavailableDates;
    Button bookButton;
    Button enterDateDisableButton;
    TextView dateSelected;
    Appointment appointment = new Appointment();
    Appointment checkDBAppointment = new Appointment();
    Spinner hourPicker;
    User signedInUser = new User();
    String userObj;
    int appBooked = 0;
    DateTime dt = new DateTime();
    int hour;
    int minute;
    String fullHour;
    String[] hoursArray;
    String[] finalHoursArray;
    Calendar now = Calendar.getInstance();
    DatePickerDialog datePickerDialog;
    ArrayList<Calendar> datesToDisableArray = new ArrayList<>();
    ArrayList<String> startHourArray = new ArrayList<>();
    ArrayList<String> endHourArray = new ArrayList<>();
    String[] hours = {"08:00", "08:30", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00",
            "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30",
            "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);
        Log.d("Lifecycle: ", " onCrate SchedulerActivity");
        dbAppointments = FirebaseDatabase.getInstance().getReference().child("Appointments");// enable read/write to DB
        dbUnavailableDates = FirebaseDatabase.getInstance().getReference().child("Unavailable Dates");

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Lifecycle: ", " onStart SchedulerActivity");

        bookButton = findViewById(R.id.buttonBookScheduler);
        enterDateDisableButton = findViewById(R.id.buttonEnterDateDisable);

        dateSelected = findViewById(R.id.info_textDateSelected);
        hour = dt.toLocalTime().getHourOfDay() + 3;
        minute = dt.toLocalTime().getMinuteOfHour();
        fullHour = hour + ":" + minute;

        hourPicker = findViewById(R.id.spinnerHourPicker);

        /*hoursArray = UpdatedHoursArray(hours, fullHour);//,datePicker);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, hoursArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hourPicker.setAdapter(adapter);
        hourPicker.setOnItemSelectedListener(this);*/

        userObj = getIntent().getStringExtra("userObj");

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Lifecycle: ", " onResume SchedulerActivity");
        try {
            signedInUser = JsonIO.JsonString_to_Object(userObj, User.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        dbUnavailableDates.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("Lifecycle: ", " onDataChange dbUnavailableDates");
                for (DataSnapshot dsp : snapshot.getChildren()) {
                    Calendar cal = Calendar.getInstance();
                    UnavailableDate und = dsp.getValue(UnavailableDate.class);
                    String date = und.getDate();
                    String startHour = und.getStartHour();
                    String endHour = und.getEndHour();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                    try {
                        cal.setTime(sdf.parse(date));
                        datesToDisableArray.add(cal);
                        startHourArray.add(startHour);
                        endHourArray.add(endHour);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                Log.d("Lifecycle: ", " inside onResume hoursArray");
                hoursArray = UpdatedHoursArray(hours, fullHour);
                //finalHoursArray = DisableAdminHours(hoursArray, startHourArray, endHourArray);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(SchedulerActivity.this, android.R.layout.simple_spinner_item, hoursArray);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                hourPicker.setAdapter(adapter);
                hourPicker.setOnItemSelectedListener(SchedulerActivity.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        enterDateDisableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = DatePickerDialog.newInstance(SchedulerActivity.this, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                configureDatePickerDialog(datesToDisableArray, startHourArray, endHourArray);
                datePickerDialog.setTitle("Choose A Date");
                datePickerDialog.show(getSupportFragmentManager(), "DatePicker");
            }
        });

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Lifecycle: ", " bookButton onClick SchedulerActivity");

                appointment.setUserName(signedInUser.getFullName());

                dbAppointments.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Log.d("Lifecycle: ", "bookButton onDataChange SchedulerActivity");
                        if (snapshot.getValue() == null) {
                            writeNewAppointment(signedInUser.getId(), appointment);

                            Toast.makeText(SchedulerActivity.this, "Appointment Booked !", Toast.LENGTH_LONG).show();

                            Intent userBookedAppsIntent = new Intent(getApplicationContext(), UserBookedAppsActivity.class);// go to Main Menu
                            userBookedAppsIntent.putExtra("userObj", userObj);
                            startActivity(userBookedAppsIntent);
                        } else {
                            for (DataSnapshot dsp : snapshot.getChildren()) { //enhanced loop
                                checkDBAppointment = dsp.getValue(Appointment.class);
                                String uid = dsp.getKey();
                                if (!uid.equals(signedInUser.getId())) {
                                    Log.d("Lifecycle: ", "bookButton onDataChange IF not same UID SchedulerActivity");
                                    if (checkDBAppointment.getHour().equals((appointment.getHour())) && checkDBAppointment.getDate().equals(appointment.getDate())) {
                                        Log.d("Lifecycle: ", "bookButton onDataChange IF SchedulerActivity");
                                        appBooked = 1;
                                        break;
                                    }
                                }
                            }
                            if (appBooked == 1) {
                                Log.d("Lifecycle: ", "bookButton onDataChange appBooked = 1 SchedulerActivity");
                                Toast.makeText(SchedulerActivity.this, "Appointment unavailable ", Toast.LENGTH_LONG).show();
                            } else { // if appointment is available
                                Log.d("Lifecycle: ", "bookButton onDataChange ELSE SchedulerActivity");
                                writeNewAppointment(signedInUser.getId(), appointment);

                                Toast.makeText(SchedulerActivity.this, "Appointment Booked !", Toast.LENGTH_LONG).show();

                                Intent userBookedAppsIntent = new Intent(getApplicationContext(), UserBookedAppsActivity.class);// go to Main Menu
                                userBookedAppsIntent.putExtra("userObj", userObj);
                                startActivity(userBookedAppsIntent);

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


    public void writeNewAppointment(String userId, Appointment appointment) {

        dbAppointments.child(userId).setValue(appointment);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String hourSelected = parent.getItemAtPosition(position).toString();
        appointment.setHour(hourSelected);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public static String[] UpdatedHoursArray(String[] hours, String currentHour) {

        int counter = 0;

        Date dateCurrentHour = null;
        try {
            dateCurrentHour = new SimpleDateFormat("HH:mm").parse(currentHour);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (String s : hours) {
            int i = 0;
            try {
                Date dateChosenHour = new SimpleDateFormat("HH:mm").parse(s);
                if (dateChosenHour.before(dateCurrentHour)) {
                    counter++;
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        int relevantHoursSize = hours.length - counter;
        if (relevantHoursSize == 0) {
            // move to next date in date picker
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_YEAR, 1);
            //datePicker.setMinDate(cal.getTimeInMillis());
            return hours;
        }

        String[] relevantHours = new String[relevantHoursSize];
        for (int j = 0; j < relevantHours.length; j++) {
            relevantHours[j] = hours[counter];
            counter++;
        }

        return relevantHours;
    }

  /*  public String[] DisableAdminHours(String[] hoursArray, ArrayList<String> startHourArray, ArrayList<String> endHourArray) {
        String[] disableAdminHoursArray = new String[hours.length - startHourArray.size()];
        Date dateHoursArrayHour = null;
        Date dateStartHourArrayHour = null;
        Date dateEndHourArrayHour = null;
        boolean isHourEqualsStart;
        boolean isHourAfterStart;
        boolean isHourEqualsEnd;
        boolean isHourBeforeEnd;


        for (int i = 0; i < hoursArray.length; i++) {
            try {
                dateHoursArrayHour = new SimpleDateFormat("HH:mm").parse(hoursArray[i]);
                dateStartHourArrayHour = new SimpleDateFormat("HH:mm").parse(startHourArray.get(i));
                dateEndHourArrayHour = new SimpleDateFormat("HH:mm").parse(endHourArray.get(i));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            isHourEqualsStart = dateHoursArrayHour.equals(dateStartHourArrayHour);
            isHourAfterStart = dateHoursArrayHour.after(dateStartHourArrayHour);
            isHourEqualsEnd = dateHoursArrayHour.equals(dateEndHourArrayHour);
            isHourBeforeEnd = dateHoursArrayHour.before(dateEndHourArrayHour);

            if (!((isHourAfterStart && isHourBeforeEnd) || (isHourEqualsStart) || (isHourEqualsEnd))) {
            *//*if (! ( ( (dateStartHourArrayHour.equals(dateHoursArrayHour)) || (dateStartHourArrayHour.after(dateHoursArrayHour)) ) &&
                    ( (dateEndHourArrayHour.equals(dateHoursArrayHour)) || (dateEndHourArrayHour.before(dateHoursArrayHour)) ) )){*//*
                disableAdminHoursArray[i] = hoursArray[i];
            }
        }

        return disableAdminHoursArray;
    }*/


    // DatePickerDialog
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(year);
        appointment.setDate(date);
        dateSelected.setText(date);
        Toast.makeText(this, date, Toast.LENGTH_LONG).show();
    }


    public void configureDatePickerDialog(ArrayList<Calendar> datesToDisableArray, ArrayList<String> startHourArray, ArrayList<String> endHourArray) {
        Calendar min_date = Calendar.getInstance();
        Calendar max_date = Calendar.getInstance();
        int Year = now.get(Calendar.YEAR);
        max_date.set(Calendar.YEAR, Year + 2);
        datePickerDialog.setMaxDate(max_date);
        datePickerDialog.setMinDate(min_date);
        //Disable all FRIDAYS and SATURDAYS between Min and Max Dates
        for (Calendar loopdate = min_date; min_date.before(max_date); min_date.add(Calendar.DATE, 1), loopdate = min_date) {
            int dayOfWeek = loopdate.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == Calendar.FRIDAY || dayOfWeek == Calendar.SATURDAY) {
                Calendar[] disabledDays = new Calendar[1];
                disabledDays[0] = loopdate;
                datePickerDialog.setDisabledDays(disabledDays);
            }
        }
        // Disable Admin Unavailable dates and hours
        Calendar[] datesToDisable = new Calendar[datesToDisableArray.size()];
        for (int i = 0; i < datesToDisableArray.size(); i++) {
            if (startHourArray.get(i).equals(hours[0]) && endHourArray.get(i).equals(hours[hours.length - 1])) {
                datesToDisable[i] = datesToDisableArray.get(i);
            } else {
                // make spinner not to display hours selected by admin


            }
        }
        for (int j = 0; j < datesToDisable.length; j++) {
            if (datesToDisable[j] != null) {
                Calendar[] disabledDate = new Calendar[1];
                disabledDate[0] = datesToDisable[j];
                datePickerDialog.setDisabledDays(disabledDate);
            }

        }


    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Lifecycle: ", " onPause SchedulerActivity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Lifecycle: ", " onStop SchedulerActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Lifecycle: ", " onDestroy SchedulerActivity");
    }
}