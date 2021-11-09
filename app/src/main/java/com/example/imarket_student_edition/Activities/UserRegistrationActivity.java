package com.example.imarket_student_edition.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.imarket_student_edition.R;

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
        // Finish activity and go back to parent
        finish();
    }
}