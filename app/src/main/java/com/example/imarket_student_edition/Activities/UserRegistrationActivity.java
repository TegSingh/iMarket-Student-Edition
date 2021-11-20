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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserRegistrationActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 2;
    FusedLocationProviderClient fusedLocationProviderClient;
    String location_string = "";
    int id;
    String name, email, password, dob;
    EditText editTextNameRegistration, editTextEmailRegistration, editTextPasswordRegistration;
    DatePicker datePickerRegistration;
    UserModel user;

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

        // Create the View instance for all the Views in Main Activity
        editTextNameRegistration = findViewById(R.id.editTextNameRegistration);
        editTextEmailRegistration = findViewById(R.id.editTextEmailRegistration);
        datePickerRegistration = findViewById(R.id.datePickerRegistration);
        editTextPasswordRegistration = findViewById(R.id.editTextPasswordRegistration);

        // Get values from the edit texts and date picker

        // Add autoincrement for ID
        id = database_helper.get_all_users().size() + 1;
        name = editTextNameRegistration.getText().toString();
        email = editTextEmailRegistration.getText().toString();
        password = editTextPasswordRegistration.getText().toString();


        dob = datePickerRegistration.getYear() + "-" + datePickerRegistration.getMonth() + "-" + datePickerRegistration.getDayOfMonth();


        // Check permissions
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Both required permissions are granted
            System.out.println("Permissions granted");
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(UserRegistrationActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }

    }

    // Method to handle permission results if received
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                System.out.println("Request permissions have been granted. About to call get current location method");
                getCurrentLocation();
            } else {
                Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Method to get current user location
    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {

        System.out.println("Get current location method called");

        // Call the get current location method
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Check if GPS provider is enabled
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            // Get last known location using fused location provider client
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    System.out.println("On complete handler called from fused location provider client");
                    Location location = task.getResult();

                    if (location != null) {
                        System.out.println("Location is not null");
                        // Get values for latitude and longitude
                        Double latitude = location.getLatitude();
                        Double longitude = location.getLongitude();
                        // Get address using geocoder
                        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                        try {
                            List<Address> generated_addresses = geocoder.getFromLocation(latitude, longitude, 1);
                            if (generated_addresses.size() == 0) {
                                System.out.println("Geocoder cannot generate address");
                            } else {
                                System.out.println("Generating the current address");
                                Address generated_address = generated_addresses.get(0);
                                location_string = generated_address.getAddressLine(0).toString();
                                System.out.println(location_string);

                                System.out.println("About to call insert user method");


                                // Get the current date and convert to string
                                Date date1 = new Date(); // Add current
                                String date_created = date1.toString();
                                user = new UserModel(id, name, email, password, dob, location_string, date_created);
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
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {
                        System.out.println("Location is null");
                        // Initialize the location request object
                        LocationRequest locationRequest = new LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(10000)
                                .setFastestInterval(1000)
                                .setNumUpdates(1);
                        // Set location callback
                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                Location location1 = locationResult.getLastLocation();
                                Double latitude = location1.getLatitude();
                                Double longitude = location1.getLongitude();
                                // Get address using geocoder
                                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                                try {
                                    List<Address> generated_addresses = geocoder.getFromLocation(latitude, longitude, 1);
                                    if (generated_addresses.size() == 0) {
                                        System.out.println("Geocoder cannot generate address");
                                    } else {
                                        System.out.println("Generating the current address if location is null");
                                        Address generated_address = generated_addresses.get(0);
                                        location_string = generated_address.getAddressLine(0).toString();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        };
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                    }
                }
            });
        }

    }

    // Method to print the list of users in the table [MAINLY FOR TESTING]
    private void print_user_list(ArrayList<UserModel> user_list) {
        System.out.println("Print user list method called");
        for (UserModel user: user_list) {
            System.out.println(user.toString());
        }
    }
}