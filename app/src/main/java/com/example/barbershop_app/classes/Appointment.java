package com.example.barbershop_app.classes;

public class Appointment {

    private int year;
    private int month;
    private int dayOfMonth;
    private String hour;


    public Appointment(){
        //this.year = 0;
        //this.month = 0;
        //this.dayOfMonth = 0;
        //this.hour = "0";
    }

    public Appointment(int year , int month , int dayOfMonth , String hour){
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.hour = hour;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }


}
