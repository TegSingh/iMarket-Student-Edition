package com.example.imarket_student_edition;

import java.util.Date;

public class UserModel {
    private int id;
    private String name;
    private String email;
    private String password;
    private Date dob;
    private String location;
    private Date date_created;

    // Declare the constructor
    public UserModel(int id, String name, String email, String password, Date dob, String location, Date date_created) {
        System.out.println("Constructor for the model USER called");
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.dob = dob;
        this.location = location;
        this.date_created = date_created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    // Overwrite the toString method
    @Override
    public String toString() {
        return "User ID: " + this.id + " Name: " + this.name + " Location: " + this.location;
    }
}
