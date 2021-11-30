package com.example.imarket_student_edition.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
        TextView userName;
        String user_Name,user_id;

        private ArrayList<ProductModel> productModelList;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        System.out.println("called the product page");
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.home);
        userName = findViewById(R.id.UserLoginName);
            if(getIntent().hasExtra("email")) {
                user_Name = getIntent().getStringExtra("email");
                user_Name = databaseHelper.userName(user_Name);
                user_id = databaseHelper.userID();
                //System.out.println(user_id+ "   - this is the id for the user ********");
                userName.setText( "Logged in as: "+ user_Name);
            }else {
                userName.setText("No user detected");
            }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
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
        productModelList = databaseHelper.get_all_products();
        RecyclerView recyclerView = findViewById(R.id.products_recycleView);
        customAdapter = new CustomAdapter(ProductPageActivity.this,this, productModelList);
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
                        Intent intent = new Intent(ProductPageActivity.this, UserActivity.class);
                        intent.putExtra("UserName",user_Name);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        return  true;

                    case R.id.bookmark:
                        Intent intent1 = new Intent(ProductPageActivity.this, AddProductActivity.class);
                        intent1.putExtra("UserName",user_Name);
                        intent1.putExtra("UserId", user_id);
                        startActivity(intent1);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

}
