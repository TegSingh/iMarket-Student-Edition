package com.example.imarket_student_edition.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imarket_student_edition.DatabaseHelper.MyDatabase;
import com.example.imarket_student_edition.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProductActivity extends AppCompatActivity {

    private Cursor cursor;
    private ImageView p_image;
    private ImageButton imageButton;
    private BottomNavigationView bottomNavigationView;
    private TextView p_name, p_price, p_description, c_name, c_number;
    private String pdbName, pdbPrice, pdbDescription, pdbImage, cdbNumber, userID;
    public String uname;
    MyDatabase db_helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        db_helper = new MyDatabase(ProductActivity.this);
        p_name = findViewById(R.id.product_name);
        p_price = findViewById(R.id.product_price);
        p_description = findViewById(R.id.product_description);
        p_image = findViewById(R.id.product_image);

        bottomNavigationView = findViewById(R.id.bottom_nav);

        c_name = findViewById(R.id.contact_name);
        c_number = findViewById(R.id.contact_number);

        imageButton = findViewById(R.id.imageButton3);

        getIntentData();

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProductActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });
        bottomNavSelection();
    }

    public void getIntentData() {
        if(getIntent().hasExtra("name") && getIntent().hasExtra("price") &&
                getIntent().hasExtra("condition") && getIntent().hasExtra("image") &&
                getIntent().hasExtra("uid")){
            pdbName = getIntent().getStringExtra("name");
            pdbPrice = getIntent().getStringExtra("price");
            pdbDescription= getIntent().getStringExtra("condition");
            pdbImage = getIntent().getStringExtra("image");
            userID = getIntent().getStringExtra("uid");

            cdbNumber = getIntent().getStringExtra("unumber");

            // store the string
            p_name.setText(pdbName);
            p_description.setText(pdbDescription);
            p_price.setText(pdbPrice);
            p_image.setImageBitmap(BitmapFactory.decodeFile(String.valueOf(pdbImage)));

            c_number.setText(cdbNumber);

            getContactData();

        }else {
            Toast.makeText(ProductActivity.this, "No data found.", Toast.LENGTH_SHORT).show();
        }
    }

    public void getContactData(){
        cursor = db_helper.get_user_profile(Integer.parseInt(userID));
        if(cursor.getCount() == 0) {
            Toast.makeText(ProductActivity.this, "No data found in database!", Toast.LENGTH_SHORT).show();

        } else if(cursor.getCount() > 0){
            cursor.moveToFirst();
            c_name.setText(cursor.getString(1));
        }
    }


    public void bottomNavSelection() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.profile:
                        Intent intent = new Intent(ProductActivity.this, ProfileActivity.class);
                        intent.putExtra("UserName",uname);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        Intent i = new Intent(ProductActivity.this, HomeActivity.class);
                        i.putExtra("UserName",uname);
                        startActivity(i);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.addPost:
                        Intent intent1 = new Intent(ProductActivity.this, AddProductActivity.class);
                        intent1.putExtra("UserName",uname);
                        startActivity(intent1);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

}