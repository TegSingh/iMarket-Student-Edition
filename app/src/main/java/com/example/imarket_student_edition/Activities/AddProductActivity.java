package com.example.imarket_student_edition.Activities;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imarket_student_edition.DatabaseHelper.MyDatabase;
import com.example.imarket_student_edition.Models.ProductModel;
import com.example.imarket_student_edition.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddProductActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FloatingActionButton saveProductButton;
    EditText productName,productPrice, productCondition,productUserNumber, productLoc;
    TextView productUserName;
    String productNameInput,productPriceInput, productConditionInput,productUserNameInput,
            productUserNumberInput, date_Added, product_location;
    String  imagePath = "No Image";
    int product_id, user_id;
    ProductModel product;
    Boolean validation = false;
    ImageView image_selection,imageDestination;
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;
    private static final int CAMERA_REQUEST = 100;

    // ADD MY DATABASE HELPER
    MyDatabase database_helper = new MyDatabase(AddProductActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        // DEFINE ALL THE VIEWS TO A VARIABLE
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.addPost);
        saveProductButton = findViewById(R.id.s_button);
        productName = findViewById(R.id.ProductName);
        productPrice = findViewById(R.id.ProductPrice);
        productCondition = findViewById(R.id.ProductCondition);
        productUserName = findViewById(R.id.UpdatePage_userName);
        productUserNumber = findViewById(R.id.ProductUserNumber);
        productLoc = findViewById(R.id.productLocation);
        image_selection = findViewById(R.id.Select_image);
        imageDestination = findViewById(R.id.product_image);
        // Call the bottom navigation function
        bottomNavSelection();

        // call current user info method to get the session information
        get_current_user_info();

        saveProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // call the get product data method to validate the user inputs and read the input from the user
                if(getProductData()) {
                    // call the product model to store the user inputs
                    product = new ProductModel(product_id, productNameInput, imagePath,
                            productConditionInput, date_Added, productPriceInput, user_id,
                            productUserNumberInput, product_location);
                    // Insert the product information into database with the help of "ProductModel.java"

                    // Call the insert product method from database
                    boolean result = database_helper.insert_product(product);
                    if (result) {
                        // Print the product all the product information in the terminal
                        ArrayList<ProductModel> product_list = database_helper.get_all_products();
                        print_product_list(product_list);
                        System.out.println("Product Added successfully");
                        // Toast message for successfully adding the product information in the database
                        Toast.makeText(AddProductActivity.this, "Product added successfully", Toast.LENGTH_SHORT).show();
                        // Traversing back to the home activity after clicking save button
                        Intent intent = new Intent(AddProductActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                }else {
                    Toast.makeText(AddProductActivity.this, "Could not register user", Toast.LENGTH_SHORT).show();
                }

            }
        });
        // on click for camera image icon
        image_selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // Toast.makeText(AddProductActivity.this,"image icon onclick works" , Toast.LENGTH_SHORT).show();
                // Request permission from user
                if (ContextCompat.checkSelfPermission(AddProductActivity.this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(AddProductActivity.this,
                            new String[]{
                                    Manifest.permission.CAMERA},
                            100);
                }else {
                    selectCamera();
                }
            }
        });

    }
    // gets the session information for the current user that's logged in
    public void get_current_user_info(){
        Cursor cursor;
        cursor = database_helper.getData();
        if(cursor.getCount() == 0) {
            Toast.makeText(AddProductActivity.this, "No user Detected", Toast.LENGTH_SHORT).show();

        }else if(cursor.getCount() >0){
            cursor.moveToFirst();
            productUserName.setText(cursor.getString(1));
            user_id = Integer.parseInt(cursor.getString(2));
            productUserNameInput = cursor.getString(1);
        }
    }

    // Method for selection image from gallery
    private void selectImage(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
    }
    // Method for selecting images through camera
    public void selectCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 100);
    }
    // check if the permission was granted to the action
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length > 0){

            if(grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                selectImage();
            }else{
                Toast.makeText(this,"Permission Denied" , Toast.LENGTH_SHORT).show();
            }

        }
    }
    // set the image on the screee
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK){
            if(data != null){
                Uri selectedImageUri = data.getData();
                if(selectedImageUri != null){
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        // sets the image on the screen with the help of bitmap
                        imageDestination.setImageBitmap(bitmap);
                        //Makes the image view visible
                        imageDestination.setVisibility(View.VISIBLE);
                        // get image path and store it a variable
                        imagePath = getPathFromUri(selectedImageUri);
                    } catch (Exception e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageDestination.setImageBitmap(photo);
            imageDestination.setVisibility(View.VISIBLE);
            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri_camera(getApplicationContext(), photo);
            // CALL THIS METHOD TO GET THE ACTUAL PATH
            File finalFile = new File(getRealPathFromURI(tempUri));
            imagePath =  String.valueOf(finalFile);

        }
    }
    // Gets the image path for the image taken by the camera
    public Uri getImageUri_camera(Context inContext, Bitmap inImage) {
        Bitmap OutImage = Bitmap.createScaledBitmap(inImage, 1000, 1000,true);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), OutImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }
    // Gets the image path for the image inside the gallery
    private String getPathFromUri(Uri contentUri){
        String filepath;
        Cursor cursor = getContentResolver()
                .query(contentUri, null, null,null,null);
        if(cursor == null){
            filepath = contentUri.getPath();

        }else{
            cursor.moveToFirst();
            // gets the data path
            int index = cursor.getColumnIndex("_data");
            filepath = cursor.getString(index);
            cursor.close();

        }
        return filepath;
    }

    // validating the user inputs
    public boolean getProductData(){

        product_id = database_helper.get_all_products().size() + 1;
        // if and else statement to validate user input
        if(productName.getText().toString().trim().length() == 0){
            productName.setError("Enter Product Name");
        }else if(productPrice.getText().toString().trim().length() == 0){
            productPrice.setError("Enter Product price");

        }else if (productCondition.getText().toString().trim().length() == 0){
            productCondition.setError("Product condition error");

        }else if (productLoc.getText().toString().trim().length() == 0){
            productLoc.setError("Product location error");

        }else if (productUserNumber.getText().toString().trim().length() == 0){
            productUserNumber.setError("Product contact number error");
        }else {
            productNameInput = productName.getText().toString().trim();
            productPriceInput = productPrice.getText().toString().trim();
            productConditionInput = productCondition.getText().toString().trim();
            productUserNumberInput = productUserNumber.getText().toString().trim();
            product_location = productLoc.getText().toString().trim();
            // gets the name of the city from the user input
            try {
                Geocoder geocoder = new Geocoder(this);;
                List<Address> addresses = geocoder.getFromLocationName(product_location,1);
                Address address = addresses.get(0);
                //lat-long values can be used to get address
                product_location = address.getLocality();
              //  System.out.println("*****INSIDE GET PRODUCTS DATA FUNCTION****" + product_location);
            } catch (IOException e) {
                e.printStackTrace();
            }
            validation = true;
        }

        // Set data_created as current date
        Date date = new Date();
        date_Added = date.toString();

        return validation;
    }

    private void print_product_list(ArrayList<ProductModel> product_list) {
        System.out.println("Print Product list method called");
        for (ProductModel product: product_list) {
            System.out.println(product.toString());
        }
    }

    // Method for bottom nav bar
    public void bottomNavSelection(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.profile:
                        // intent for going to profile activity
                        Intent intent = new Intent(AddProductActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        // intent for going to home activity
                        Intent intent2 = new Intent(AddProductActivity.this, HomeActivity.class);
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        return  true;

                    case R.id.addPost:
                        return true;
                }
                return false;
            }
        });
    }
}