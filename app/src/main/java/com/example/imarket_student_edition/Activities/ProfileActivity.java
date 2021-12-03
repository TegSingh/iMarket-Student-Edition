package com.example.imarket_student_edition.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imarket_student_edition.DatabaseHelper.MyDatabase;
import com.example.imarket_student_edition.Models.ProductModel;
import com.example.imarket_student_edition.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    TextView uname, user_account_created, text_below_img;
    FloatingActionButton update;
    EditText user_update_name, user_update_password, user_update_email;
    String updated_p_name, updated_p_email,  updated_p_password;
    int user_id;
    ImageView product_empty;
    CustomProfileAdapter customProfileAdapter;
    BottomNavigationView bottomNavigationView;
    private ArrayList<ProductModel> productModelList;

    // Add my database helper
    MyDatabase database_helper = new MyDatabase(ProfileActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        user_update_email = findViewById(R.id.p_user_email);
        user_update_password = findViewById(R.id.p_user_password);
        user_account_created = findViewById(R.id.p_date_created);
        product_empty = findViewById(R.id.profile_empty_product);
        text_below_img = findViewById(R.id.product_empty_txt_profile);
        update = findViewById(R.id.up_profile);

        uname = findViewById(R.id.u_name);
        user_update_name = findViewById(R.id.puser_name);
        get_current_user_id();
        //callCustomAdaptor();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updated_p_name = user_update_name.getText().toString().trim();
                updated_p_email = user_update_email.getText().toString().trim();
                updated_p_password = user_update_password.getText().toString().trim();
                database_helper.update_user_profile(String.valueOf(user_id),updated_p_name,updated_p_password,updated_p_email);
                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.profile:
                        return true;

                    case R.id.home:
                        Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return  true;

                    case R.id.addPost:
                        Intent i = new Intent(ProfileActivity.this, AddProductActivity.class);
                        startActivity(i);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
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
        productModelList = database_helper.searchPro(user_id);
        if(productModelList.isEmpty()){
            product_empty.setVisibility(View.VISIBLE);
            text_below_img.setVisibility(View.VISIBLE);
        }else {
            product_empty.setVisibility(View.GONE);
            text_below_img.setVisibility(View.GONE);
        }
        //productModelList = database_helper.get_all_products();

        RecyclerView recyclerView = findViewById(R.id.p_recycleview);
        customProfileAdapter = new CustomProfileAdapter(ProfileActivity.this,this, productModelList);
        recyclerView.setAdapter(customProfileAdapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
    }

    public void get_current_user_id(){
        Cursor cursor;
        cursor = database_helper.getData();
        //get_current_user_info();
        if(cursor.getCount() == 0) {
            Toast.makeText(ProfileActivity.this, "No data found in database!", Toast.LENGTH_SHORT).show();

        }else if(cursor.getCount() >0){
            cursor.moveToFirst();
            user_update_name.setText(cursor.getString(1));
            // IF WE NEED TO USE USER ID JUST UNCOMMENT THE STATEMENT AND SET IT TO A VAIRABLE
            user_id = Integer.parseInt(cursor.getString(2));
            //System.out.println(user_id + "*************************");
            uname.setText(cursor.getString(1));
            get_current_user_info();

        }
    }
    public void get_current_user_info(){
        Cursor cursor;
       // System.out.println(" inside the function statement*&&&*****");
        cursor = database_helper.get_user_profile(user_id);
        if(cursor.getCount() == 0) {
            Toast.makeText(ProfileActivity.this, "No data found in database!", Toast.LENGTH_SHORT).show();
        }else{
            cursor.moveToFirst();
            user_update_email.setText(cursor.getString(2));
            user_update_password.setText(cursor.getString(3));
            user_account_created.setText(cursor.getString(4));
        }
        callCustomAdaptor();
    }

}