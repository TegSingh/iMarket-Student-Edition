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
        MyDatabase databaseHelper = new MyDatabase(HomeActivity.this);
        BottomNavigationView bottomNavigationView;
        CustomAdapter customAdapter;
        ImageView locationImage;
        TextView userName, pick_location;
        String user_Name,user_id;

        private ArrayList<ProductModel> productModelList;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        System.out.println("called the product page");
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.home);
        userName = findViewById(R.id.UserLoginName);
        pick_location = findViewById(R.id.pickLocation);
        get_current_user_info();
        bottomNavSelection();
        locationImage = findViewById(R.id.imageButton2);
        callCustomAdaptor();
        locationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(HomeActivity.this,UserAuthenticationActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    //  calls CustomAdaptor java file
    public  void  callCustomAdaptor(){
        if(getIntent().hasExtra("Location")){
            String location = getIntent().getStringExtra("Location");
            System.out.println("*******" + location);
            databaseHelper.insert_location(location,user_id);
            productModelList = databaseHelper.search_products(Integer.parseInt(user_id));
            pick_location.setText(location);

        } else {
            productModelList = databaseHelper.get_all_products();
        }
        RecyclerView recyclerView = findViewById(R.id.products_recycleView);
        customAdapter = new CustomAdapter(HomeActivity.this,this, productModelList);
        recyclerView.setAdapter(customAdapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
    }
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
