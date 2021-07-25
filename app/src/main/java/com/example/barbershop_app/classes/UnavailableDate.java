package com.example.barbershop_app.classes;

public class UnavailableDate {
    String date;
    String startHour;
    String endHour;

    public UnavailableDate(){

    }

    public UnavailableDate(String date, String startHour, String stopHour) {
        this.date = date;
        this.startHour = startHour;
        this.endHour = stopHour;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String stopHour) {
        this.endHour = stopHour;
    }
}
