package com.example.imarket_student_edition.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.imarket_student_edition.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UpdateProduct extends AppCompatActivity {
    EditText productName,productPrice, productCondition;
    String  productNameInput,productPriceInput, productConditionInput;
    String  imagePath = "No Image";
    FloatingActionButton UpdateProductButton, delProductButton;
    ImageView updateImage;
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

        getIntentData();
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

        }else {
            Toast.makeText(UpdateProduct.this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }
}