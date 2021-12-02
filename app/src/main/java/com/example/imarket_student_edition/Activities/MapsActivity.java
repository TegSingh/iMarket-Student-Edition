package com.example.imarket_student_edition.Activities;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.example.imarket_student_edition.DatabaseHelper.MyDatabase;
import com.example.imarket_student_edition.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.imarket_student_edition.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    //Declaring necessary variables
    private GoogleMap mMap;
    private Geocoder geocoder;
    private ActivityMapsBinding binding;
    private ImageButton imgBtn;
    private SearchView searchView;
    private Marker marker;
    private String locationCity, userId;
    private MyDatabase databaseHelper = new MyDatabase(MapsActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         binding = ActivityMapsBinding.inflate(getLayoutInflater());
         setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //initializing geocoder object
        geocoder = new Geocoder(this);

        imgBtn = findViewById(R.id.imageButton);
        searchView = findViewById(R.id.search);

        // Get location from search view and add marker
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                System.out.println("User searched for a location");
                //getting user query
                String location = searchView.getQuery().toString();
                try {
                    List<Address> addresses = geocoder.getFromLocationName(location,1);
                    Address address = addresses.get(0);
                    //latlng values can be used to get address
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    //storing city name
                    locationCity = address.getLocality();
                    // Removes old markers
                    if(marker != null) marker.remove();
                    // Add marker on searched location
                    marker = mMap.addMarker(new MarkerOptions().position(latLng).title(address.getAddressLine(0)));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,13));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        //Move back to the products home page
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create the intent for the new activity and start the activity
                Intent i = new Intent(MapsActivity.this, HomeActivity.class);
                i.putExtra("Location",locationCity);
                startActivity(i);
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        System.out.println("Map is opened");
        mMap = googleMap;

        /**Option to drag, select, and mark location*/
       /* mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(@NonNull LatLng latLng) {
                try {
                    List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    String addressLine = addresses.get(0).getAddressLine(0);
                    if(marker != null) marker.remove();
                    marker = mMap.addMarker(new MarkerOptions().position(latLng).title(addressLine));
                    System.out.println("last location clicked: " + latLng.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });*/

        /**SETTING DEFAULT LOCATION*/
        LatLng toronto = new LatLng(43.65708, -79.37395);
        //MoveS camera to Toronto (zoomed out view)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(toronto));
    }
}