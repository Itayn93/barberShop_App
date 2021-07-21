package com.example.barbershop_app.classes;

public class Appointment {

    private String userName;
    private String date;
    private String hour;

    public Appointment(){

    }

    public Appointment(int year , int month , int dayOfMonth , String hour){
        this.date = String.valueOf(dayOfMonth) + "/" + String.valueOf(month) + "/" + String.valueOf(year);
        this.hour = hour;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }


}
