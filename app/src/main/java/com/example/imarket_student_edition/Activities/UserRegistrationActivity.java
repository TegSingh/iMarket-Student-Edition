package com.example.imarket_student_edition.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.imarket_student_edition.DatabaseHelper.MyDatabase;
import com.example.imarket_student_edition.Models.UserModel;
import com.example.imarket_student_edition.R;

import java.util.ArrayList;
import java.util.Date;

public class UserRegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        // Get the intent in case any date needs to be accessed
        Intent i = getIntent();
    }

    // Method to register the user once the form has been submitted
    public void registerUser(View v) {
        System.out.println("Method to Register User called from user registration activity. Going back to Login");

        // Add my database helper
        MyDatabase database_helper = new MyDatabase(this);

        // Create the View instance for all the Views in Main Activity
        EditText editTextNameRegistration = findViewById(R.id.editTextNameRegistration);
        EditText editTextEmailRegistration = findViewById(R.id.editTextEmailRegistration);
        DatePicker datePickerRegistration = findViewById(R.id.datePickerRegistration);
        EditText editTextPasswordRegistration = findViewById(R.id.editTextPasswordRegistration);

        // Get values from the edit texts and date picker

        // Add autoincrement for ID
        int id = database_helper.get_all_users().size() + 1;
        String name = editTextNameRegistration.getText().toString();
        String email = editTextEmailRegistration.getText().toString();
        String password = editTextPasswordRegistration.getText().toString();

        // REQUIRES LOCATION TRACKING FUNCTIONALITY
        String location = "RANDOM LOCATION"; // Add a random location
        String dob = datePickerRegistration.getYear() + "-" + datePickerRegistration.getMonth() + "-" + datePickerRegistration.getDayOfMonth();
        Date date1 = new Date(); // Add current
        String date_created = date1.toString();

        UserModel user = new UserModel(id, name, email, password, dob, location, date_created);
        System.out.println("About to call insert user method");

        // Call the database helper method to add user
        boolean result = database_helper.insert_user(user);

        if (result) {
            System.out.println("User Registered successfully");

            // Print user list to the terminal after registering the user
            ArrayList<UserModel> user_list = database_helper.get_all_users();
            print_user_list(user_list);
        } else {
            System.out.println("Could not add user to the table");
        }

        // Finish activity and go back to parent
        finish();
    }

    // Method to print the list of users in the table [MAINLY FOR TESTING]
    private void print_user_list(ArrayList<UserModel> user_list) {
        System.out.println("Print user list method called");
        for (UserModel user: user_list) {
            System.out.println(user.toString());
        }
    }
}