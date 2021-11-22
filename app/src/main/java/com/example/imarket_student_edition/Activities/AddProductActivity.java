package com.example.imarket_student_edition.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.imarket_student_edition.DatabaseHelper.MyDatabase;
import com.example.imarket_student_edition.Models.ProductModel;
import com.example.imarket_student_edition.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;

public class AddProductActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FloatingActionButton saveProductButton;
    EditText productName,productPrice, productCondition,productUserName,productUserNumber;
    String productNameInput,productPriceInput, productConditionInput,productUserNameInput,productUserNumberInput;
    String location, date_Added, image;
    int id, user_id;
    ProductModel product;
    Float productPrice_temp;

    // Add my database helper
    MyDatabase database_helper = new MyDatabase(AddProductActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.bookmark);
        bottomNavSelection();
        saveProductButton = findViewById(R.id.save_button);
        productName = findViewById(R.id.ProductName);
        productPrice = findViewById(R.id.ProductPrice);
        productCondition = findViewById(R.id.ProductCondition);
        productUserName = findViewById(R.id.ProductUserName);
        productUserNumber = findViewById(R.id.ProductUserNumber);

        saveProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProductData();
                // Create a user model
                product = new ProductModel(id,productNameInput, image,productConditionInput, date_Added, productPriceInput,user_id);
                boolean result = database_helper.insert_product(product);
                if (result) {
                    // Print the updated list
                    ArrayList<ProductModel> product_list = database_helper.get_all_products();
                    print_product_list(product_list);
                    System.out.println("Product Added successfully");
                    Toast.makeText(AddProductActivity.this, "Product added successfully", Toast.LENGTH_SHORT).show();
                    Intent intent  = new Intent(AddProductActivity.this, ProductPageActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AddProductActivity.this, "Could not register user", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }





    public void getProductData(){

        id = database_helper.get_all_products().size() + 1;
        user_id = database_helper.get_all_products().size() + 1;
        productNameInput = productName.getText().toString().trim();
        productPriceInput = productPrice.getText().toString().trim();
        productConditionInput = productCondition.getText().toString().trim();
        productUserNameInput = productUserName.getText().toString().trim();
        productUserNumberInput = productUserNumber.getText().toString().trim();

        image = "No Image";
        // Set data_created as current date
        Date date = new Date();
        date_Added = date.toString();

    }
        private void print_product_list(ArrayList<ProductModel> product_list) {
            System.out.println("Print Product list method called");
            for (ProductModel product: product_list) {
                System.out.println(product.toString());
            }
        }


    public void bottomNavSelection(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.user:
                        startActivity(new Intent(AddProductActivity.this, UserActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(AddProductActivity.this, ProductPageActivity.class));
                        overridePendingTransition(0,0);
                        return  true;
                    case R.id.bookmark:
                        return true;
                }

                return false;
            }
        });
    }
}