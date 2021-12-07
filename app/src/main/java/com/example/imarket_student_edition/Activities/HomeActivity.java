package com.example.imarket_student_edition.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imarket_student_edition.DatabaseHelper.MyDatabase;
import com.example.imarket_student_edition.Models.ProductModel;
import com.example.imarket_student_edition.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
        // ADD MY DATABASE HELPER
        MyDatabase databaseHelper = new MyDatabase(HomeActivity.this);
        BottomNavigationView bottomNavigationView;
        CustomAdapter customAdapter;
        ImageView locationImage, empty_txt_img;
        TextView userName, pick_location, empty_txt;
        String user_Name,user_id;

        private ArrayList<ProductModel> productModelList;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // DEFINE ALL THE VIEWS TO A VARIABLE
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.home);
        userName = findViewById(R.id.UserLoginName);
        pick_location = findViewById(R.id.pickLocation);
        empty_txt = findViewById(R.id.text_empty_home_pg);
        empty_txt_img = findViewById(R.id.empty_product_image_home);
        locationImage = findViewById(R.id.imageButton2);
        // call current user info method to get the session information
        get_current_user_info();
        // Call the bottom navigation function
        bottomNavSelection();
        // call custom adapter method for initiate the recycle view
        callCustomAdaptor();

        locationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // switch to maps activity
                Intent intent = new Intent(HomeActivity.this,MapsActivity.class);
                startActivity(intent);
            }
        });
    }
    //  menu inflater for the top navigation
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }
    // logout button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(HomeActivity.this,UserAuthenticationActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
    // recreate the activity after making changes in the custom adapter file
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    //  calls CustomAdaptor java file
    public  void  callCustomAdaptor(){
            // check if the user has wish to filter the recycle view with the location
        if(getIntent().hasExtra("Location")){
            String location = getIntent().getStringExtra("Location");
          //  System.out.println("*******" + location);
            //databaseHelper.insert_location(location,user_id);
            // call the search function in database file to search by location
            productModelList = databaseHelper.search_products(location);
            pick_location.setText(location);

        } else {
            // to display all product from database
            productModelList = databaseHelper.get_all_products();
            if(productModelList.isEmpty()){
                // if database is empty then show the empty database image on screen
                empty_txt.setVisibility(View.VISIBLE);
                empty_txt_img.setVisibility(View.VISIBLE);
            }else {
               
                empty_txt.setVisibility(View.GONE);
                empty_txt_img.setVisibility(View.GONE);
            }
        }
        RecyclerView recyclerView = findViewById(R.id.products_recycleView);
        customAdapter = new CustomAdapter(HomeActivity.this,this, productModelList);
        recyclerView.setAdapter(customAdapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
    }
    //method to get user info
    public void get_current_user_info(){
        Cursor cursor;
        cursor = databaseHelper.getData();
        if(cursor.getCount() == 0) {
            Toast.makeText(HomeActivity.this, "No data found in database!", Toast.LENGTH_SHORT).show();

        } else if(cursor.getCount() > 0){
            cursor.moveToFirst();
            userName.setText( "Logged in as: "+ cursor.getString(1));
            user_id = cursor.getString(2);
            user_Name = cursor.getString(1);
            }
    }
    //navigate between the three main pages from the bottom nav bar
    public void bottomNavSelection() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.profile:
                        Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        return  true;

                    case R.id.addPost:
                        Intent intent1 = new Intent(HomeActivity.this, AddProductActivity.class);
                        startActivity(intent1);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

}
