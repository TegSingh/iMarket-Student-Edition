package com.example.imarket_student_edition.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.imarket_student_edition.DatabaseHelper.MyDatabase;
import com.example.imarket_student_edition.Models.ProductModel;
import com.example.imarket_student_edition.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ProductPageActivity extends AppCompatActivity {
        MyDatabase databaseHelper = new MyDatabase(ProductPageActivity.this);
        BottomNavigationView bottomNavigationView;
        CustomAdapter customAdapter;
        ImageView locationImage;
        private ArrayList<ProductModel> productModelList;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);
        System.out.println("called the product page");
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavSelection();
        locationImage = findViewById(R.id.imageButton2);

        callCustomAdaptor();


        locationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductPageActivity.this,MapsActivity.class);
                startActivity(intent);
            }
        });
    }
    //  calls CustomAdaptor java file
    public  void  callCustomAdaptor(){
        productModelList = databaseHelper.get_all_products();
        RecyclerView recyclerView = findViewById(R.id.products_recycleView);
        customAdapter = new CustomAdapter(ProductPageActivity.this, productModelList);
        recyclerView.setAdapter(customAdapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
    }

    public void bottomNavSelection() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.user:
                        startActivity(new Intent(ProductPageActivity.this, UserActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        return  true;

                    case R.id.bookmark:
                        startActivity(new Intent(ProductPageActivity.this, AddProductActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });
    }

}
