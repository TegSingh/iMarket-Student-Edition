package com.example.imarket_student_edition.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.imarket_student_edition.DatabaseHelper.MyDatabase;
import com.example.imarket_student_edition.R;

public class UserAuthenticationActivity extends AppCompatActivity {

    //Declaring necessary variables
    EditText editTextEmail, editTextPassword;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_authentication);
    }

    // Method to Log the user in
    public void loginButtonClickListener(View v) {
        System.out.println("Login button was clicked");

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        email = editTextEmail.getText().toString();
        password = editTextPassword.getText().toString();
        //System.out.println("Signing in: Email: " + email);

        // Get DataBase helper for checking user
        MyDatabase database_helper = new MyDatabase(this);
        boolean result = database_helper.authenticateUser(email, password);

        if(result){
            Toast.makeText(this, "User Authenticated Successfully!", Toast.LENGTH_SHORT).show();
            // Reset the values in edit text

            editTextEmail.setText("");
            editTextPassword.setText("");
            // ADD ANY REQUIRED INTENTS OR LINKS HERE

            startProductPageActivity(email);
        } else {
            Toast.makeText(this, "Invalid Email or Password!", Toast.LENGTH_SHORT).show();
            // Reset the values in edit text
            editTextEmail.setText("");
            editTextPassword.setText("");
            // ADD ANY REQUIRED INTENTS OR ERROR MESSAGES HERE
        }
    }

    // Method to move to the registration activity
    public void startRegistrationActivity(View v) {
        System.out.println("Sign up button was clicked");
        // Create the intent for the new activity and start the activity
        Intent i = new Intent(this, UserRegistrationActivity.class);
        startActivity(i);
    }

    //Method to move to the products home page
    public void startProductPageActivity(String email) {
        System.out.println("Opening Product Page Activity...");
        // Create the intent for the new activity and start the activity
        Intent i = new Intent(this, ProductPageActivity.class);
        i.putExtra("email",email);
        startActivity(i);
    }

    //Method to move to the login page
    public void startUserAuthenticationActivity() {
        System.out.println("Opening Login Page...");
        // Create the intent for the new activity and start the activity
        Intent i = new Intent(this, UserAuthenticationActivity.class);
        startActivity(i);
    }

}