package com.example.imarket_student_edition.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.imarket_student_edition.DatabaseHelper.MyDatabase;
import com.example.imarket_student_edition.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UpdateProductActivity extends AppCompatActivity {
    EditText productName,productPrice, productCondition, update_page_user_name;
    String  productNameInput,productPriceInput, productConditionInput, uname;
    String  imagePath = "No Image";
    FloatingActionButton UpdateProductButton, delProductButton;
    ImageView updateImage;
    int user_id;
    BottomNavigationView bottomNavigationView;
    MyDatabase database_helper = new MyDatabase(UpdateProductActivity.this);
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

        UpdateProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productNameInput =  productName.getText().toString().trim();
                productPriceInput = productPrice.getText().toString().trim();
                productConditionInput= productCondition.getText().toString().trim();
                database_helper.updateProduct(String.valueOf(user_id),productNameInput, productConditionInput,productPriceInput);
                Intent intent = new Intent(UpdateProductActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        delProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDelete();
            }
        });
    }

    void confirmDelete(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete \"" + productNameInput + " \" ?");
        builder.setMessage(" Are you you sure you wish to delete \"" + productNameInput + " \" ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabase database = new MyDatabase(UpdateProductActivity.this);
                database.DeleteProduct(String.valueOf(user_id));
                finish();
                Intent intent = new Intent(UpdateProductActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();
    }

    public void getIntentData() {
        if(getIntent().hasExtra("name") && getIntent().hasExtra("price") && getIntent().hasExtra("condition") && getIntent().hasExtra("image")){
            productNameInput = getIntent().getStringExtra("name");
            productPriceInput = getIntent().getStringExtra("price");
            productConditionInput= getIntent().getStringExtra("condition");
            imagePath = getIntent().getStringExtra("image");
            user_id = Integer.parseInt(getIntent().getStringExtra("uid"));
            // store the string
            productName.setText(productNameInput);
            productCondition.setText(productConditionInput);
            productPrice.setText(productPriceInput);
            updateImage.setImageBitmap(BitmapFactory.decodeFile(String.valueOf(imagePath)));
            get_current_user_info();

        }else {
            Toast.makeText(UpdateProductActivity.this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    public void get_current_user_info(){
        Cursor cursor;
        cursor = database_helper.getData();
        if(cursor.getCount() == 0) {
            Toast.makeText(UpdateProductActivity.this, "No data found in database!", Toast.LENGTH_SHORT).show();

        }else if(cursor.getCount() >0){
            cursor.moveToFirst();
            update_page_user_name.setText(cursor.getString(1));
            // IF WE NEED TO USE USER ID JUST UNCOMMENT THE STATEMENT AND SET IT TO A VARIABLE
            //user_id = Integer.parseInt(cursor.getString(2));
        }
    }

    public void bottomNavSelection() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.profile:
                        Intent intent = new Intent(UpdateProductActivity.this, ProfileActivity.class);
                        intent.putExtra("UserName",uname);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        Intent i = new Intent(UpdateProductActivity.this, HomeActivity.class);
                        i.putExtra("UserName",uname);
                        startActivity(i);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.addPost:
                        Intent intent1 = new Intent(UpdateProductActivity.this, AddProductActivity.class);
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