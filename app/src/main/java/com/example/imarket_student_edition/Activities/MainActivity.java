package com.example.imarket_student_edition.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.imarket_student_edition.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Button event listener to start the application
    public void startMarket(View v) {
        System.out.println("Button to the start application clicked. Moving to Login page");
        // Create intent to move to login activity and start activity
        Intent start_application_intent = new Intent(this, UserAuthenticationActivity.class);
        startActivity(start_application_intent);
    }
}