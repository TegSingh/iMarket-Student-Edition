package com.example.imarket_student_edition.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.imarket_student_edition.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UpdateProduct extends AppCompatActivity {
    EditText productName,productPrice, productCondition, update_page_user_name;
    String  productNameInput,productPriceInput, productConditionInput, uname;
    String  imagePath = "No Image";
    FloatingActionButton UpdateProductButton, delProductButton;
    ImageView updateImage;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        productName = findViewById(R.id.p_name_update);
        productCondition = findViewById(R.id.p_condition_update);
        productPrice = findViewById(R.id.p_price_update);
        UpdateProductButton = findViewById(R.id.up_button);
        delProductButton = findViewById(R.id.del_button);
        updateImage = findViewById(R.id.update_img);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        update_page_user_name = findViewById(R.id.UpdatePage_userName1);
        getIntentData();
        bottomNavSelection();
    }

    public void getIntentData() {
        if(getIntent().hasExtra("name") && getIntent().hasExtra("price") && getIntent().hasExtra("condition") && getIntent().hasExtra("image")){
            productNameInput = getIntent().getStringExtra("name");
            productPriceInput = getIntent().getStringExtra("price");
            productConditionInput= getIntent().getStringExtra("condition");
            imagePath = getIntent().getStringExtra("image");
            // store the string
            productName.setText(productNameInput);
            productCondition.setText(productConditionInput);
            productPrice.setText(productPriceInput);
            updateImage.setImageBitmap(BitmapFactory.decodeFile(String.valueOf(imagePath)));
            if(getIntent().hasExtra("UserName")) {
               // System.out.println("Inside the userName intent on Add product");
                uname = getIntent().getStringExtra("UserName");
                update_page_user_name.setText(uname);

            }else {
                update_page_user_name.setText("No user detected");
            }

        }else {
            Toast.makeText(UpdateProduct.this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    public void bottomNavSelection() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.user:
                        Intent intent = new Intent(UpdateProduct.this, UserActivity.class);
                        intent.putExtra("UserName",uname);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        Intent i = new Intent(UpdateProduct.this, ProductPageActivity.class);
                        i.putExtra("UserName",uname);
                        startActivity(i);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.bookmark:
                        Intent intent1 = new Intent(UpdateProduct.this, AddProductActivity.class);
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