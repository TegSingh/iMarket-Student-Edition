package com.example.imarket_student_edition.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.imarket_student_edition.DatabaseHelper.MyDatabase;
import com.example.imarket_student_edition.Models.UserModel;
import com.example.imarket_student_edition.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserRegistrationActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 2;
    EditText editTextNameRegistration, editTextEmailRegistration, editTextPasswordRegistration;
    FusedLocationProviderClient fusedLocationProviderClient;
    UserModel user;

    String name, email, password, location, date_created;
    boolean validate = false;
    int id;

    // Add my database helper
    MyDatabase database_helper = new MyDatabase(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        // Get the intent in case any date needs to be accessed
        Intent i = getIntent();
        // Set the fused location provider client
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }

    // Method to register the user once the form has been submitted
    @SuppressLint("WrongViewCast")
    public void registerUser(View v) {
        System.out.println("Method to Register User called from user registration activity. Going back to Login");
        // Call method to set the input values
        if(get_input_values()){
            // Create a user model
            user = new UserModel(id, name, email, password, location, date_created);
            System.out.println("User to be added: " + user.toString());
            // Use database helper to add the user to the database
            boolean result = database_helper.insert_user(user);
            if (result) {
                // Print the updated list
                ArrayList<UserModel> user_list = database_helper.get_all_users();
                print_user_list(user_list);
                System.out.println("User registered successfully");
                Toast.makeText(UserRegistrationActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                startUserAuthenticationActivity();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Could not register user", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to get user inputs
    public boolean get_input_values() {
        // Create the View instance for all the Views in Main Activity
        editTextNameRegistration = findViewById(R.id.editTextNameRegistration);
        editTextEmailRegistration = findViewById(R.id.editTextEmailRegistration);
        editTextPasswordRegistration = findViewById(R.id.editTextPasswordRegistration);
        // Set ID using array list size
        id = database_helper.get_all_users().size() + 1;
        // Validating user input
        if(editTextNameRegistration.getText().toString().trim().length() == 0){
                editTextNameRegistration.setError("Enter Name");
        }else if(editTextPasswordRegistration.getText().toString().trim().length() == 0){
                editTextPasswordRegistration.setError("Enter Password");
        }else if(editTextEmailRegistration.getText().toString().trim().length() == 0){
                editTextEmailRegistration.setError("Enter Email");
        }else{
            name = editTextNameRegistration.getText().toString().trim();
            email = editTextEmailRegistration.getText().toString().trim();
            password = editTextPasswordRegistration.getText().toString().trim();
            validate = true;
        }
        // NEED TO ADD LOCATION TRACKING/SOME WAY OF GEOCODING TO THIS
        location = "RANDOM LOCATION";
        // Set data_created as current date
        Date date = new Date();
        date_created = date.toString();
        return validate;
    }

    // Method to print the list of users in the table [MAINLY FOR TESTING]
    private void print_user_list(ArrayList<UserModel> user_list) {
        System.out.println("Print user list method called");
        for (UserModel user: user_list) {
            System.out.println(user.toString());
        }
    }

    //Method to move to the login page
    public void startUserAuthenticationActivity() {
        System.out.println("Opening Login Page...");
        // Create the intent for the new activity and start the activity
        Intent i = new Intent(this, UserAuthenticationActivity.class);
        startActivity(i);
    }
}