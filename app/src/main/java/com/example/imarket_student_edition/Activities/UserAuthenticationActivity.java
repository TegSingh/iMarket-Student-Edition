package com.example.imarket_student_edition.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.imarket_student_edition.R;

public class UserAuthenticationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_authentication);
    }

    // Method to Log the user in
    public void loginButtonClickListener(View v) {
        System.out.println("Login button was clicked");
        Intent i = new Intent(this, ProductPageActivity.class);
        startActivity(i);
    }

    // Method to move to the registration activity
    public void startRegistrationActivity(View v) {
        System.out.println("Sign up button was clicked");

        // Create the intent for the new activity and start the activity
        Intent i = new Intent(this, UserRegistrationActivity.class);
        startActivity(i);


    }
}