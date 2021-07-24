package com.example.barbershop_app.activities;

import androidx.appcompat.app.AppCompatActivity;

//import android.app.DatePickerDialog;
//import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.barbershop_app.R;

import java.util.Calendar;
import java.util.Date;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.date.MonthAdapter;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

public class AdminUnavailableActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener , TimePickerDialog.OnTimeSetListener {

    Button enterDate;
    Button enterTime;
    DatePickerDialog datePickerDialog ;
    TimePickerDialog timePickerDialog ;
    //int Year, Month, Day, Hour, Minute;
    Calendar now = Calendar.getInstance();
    //public static final String SHARED_PREFS = "sharedPrefs";
    //public static final String DATE = "date";
   // public static final String TIME = "time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_unavailable);
    }

    @Override
    protected void onStart() {
        super.onStart();

        enterDate = findViewById(R.id.buttonEnterDate);
        enterTime = findViewById(R.id.buttonEnterTime);
    }

    @Override
    protected void onResume() {
        super.onResume();

        enterDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datePickerDialog = DatePickerDialog.newInstance(AdminUnavailableActivity.this, now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setTitle("Choose A Date");
                datePickerDialog.show(getSupportFragmentManager(),"DatePicker");
            }
        });

        enterTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePickerDialog = TimePickerDialog.newInstance(AdminUnavailableActivity.this,now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),true);
                timePickerDialog.setTitle("Choose A Time");
                timePickerDialog.show(getSupportFragmentManager(),"DatePicker");

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

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        //saveDate(year,monthOfYear,dayOfMonth);

        Toast.makeText(this, String.format("You Selected : %d/%d/%d",dayOfMonth,monthOfYear,year),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        Toast.makeText(this, String.format("You Selected : %02d:%02d:%02d",hourOfDay,minute,second),Toast.LENGTH_LONG).show();
    }


   /* public void saveDate( int year, int monthOfYear, int dayOfMonth){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String date = String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear+1) + "/" + String.valueOf(year);

        editor.putString(DATE,date.toString());
    }*/
}