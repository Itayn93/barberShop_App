package com.example.barbershop_app;

public class User {

    private String fullName;
    private String id;
    private String email;
    private String password;

    public User(){

    }

    public User(String fullName,String id,String email,String password){

        this.fullName = fullName;
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
