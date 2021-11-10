package com.example.imarket_student_edition.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.imarket_student_edition.DatabaseHelper.MyDatabase;
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

        EditText editTextEmail = findViewById(R.id.editTextEmail);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        System.out.println("Signing in: Email: " + email);

        // Get DataBase helper for checking user
        MyDatabase database_helper = new MyDatabase(this);
        boolean result = database_helper.authenticateUser(email, password);
        if (result) {
            System.out.println("User authenticated successfully");
            // ADD ANY REQUIRED INTENTS OR LINKS HERE
        } else {
            System.out.println("Couldnt find user information");
            // ADD ANY REQUIRED INTENTS OR ERROR MESSAGES HERE
        }

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