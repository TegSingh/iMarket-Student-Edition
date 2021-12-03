package com.example.imarket_student_edition.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.imarket_student_edition.DatabaseHelper.MyDatabase;
import com.example.imarket_student_edition.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.InputStream;

public class UpdateProductActivity extends AppCompatActivity {

    EditText productName,productPrice, productCondition, update_page_user_name, productNum, update_location, update_number;
    String  productNameInput,productPriceInput, productConditionInput, uname, contactPhone, updateproductLocation, updateproductnumber;
    String  imagePath = "No Image";
    FloatingActionButton UpdateProductButton, delProductButton;
    ImageView updateImage,update_image_selection;
    int user_id;
    BottomNavigationView bottomNavigationView;
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;
    private static final int CAMERA_REQUEST = 100;
    MyDatabase database_helper = new MyDatabase(UpdateProductActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        productName = findViewById(R.id.p_name_update);
        productCondition = findViewById(R.id.p_condition_update);
        productPrice = findViewById(R.id.p_price_update);
        productNum = findViewById(R.id.ProductUserNumber);
        update_location = findViewById(R.id.productLocation_update);
        update_number = findViewById(R.id.update_user_number);
        UpdateProductButton = findViewById(R.id.up_button);
        delProductButton = findViewById(R.id.del_button);

        updateImage = findViewById(R.id.update_img);
        update_image_selection = findViewById(R.id.update_img_selection);
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
                contactPhone = productNum.getText().toString().trim();
                updateproductLocation = update_location.getText().toString().trim();
                updateproductnumber = update_number.getText().toString().trim();
                System.out.println("81888383888************"+ updateproductnumber);
                System.out.println("81888383888************"+ updateproductLocation);

                database_helper.updateProduct(String.valueOf(user_id),productNameInput, productConditionInput,productPriceInput,contactPhone,updateproductLocation,updateproductnumber, imagePath);
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


        update_image_selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UpdateProductActivity.this,"On click works" , Toast.LENGTH_SHORT).show();
                if (ContextCompat.checkSelfPermission(UpdateProductActivity.this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(UpdateProductActivity.this,
                            new String[]{
                                    Manifest.permission.CAMERA},
                            100);
                }else {
                    selectCamera();
                }
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
            updateproductLocation = getIntent().getStringExtra("ulocation");
            updateproductnumber  = getIntent().getStringExtra("updatenumber");
            //System.out.println("81888383888************"+ updateproductnumber);
            //System.out.println("81888383888************"+ updateproductLocation);
            // store the string
            productName.setText(productNameInput);
            productCondition.setText(productConditionInput);
            productPrice.setText(productPriceInput);
            update_number.setText(updateproductnumber);
            update_location.setText(updateproductLocation);

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

    private void selectImage(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
        /*if (intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
        }*/
    }
    public void selectCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 100);
    }
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
                        updateImage.setImageBitmap(bitmap);
                        updateImage.setVisibility(View.VISIBLE);
                        imagePath = getPathFromUri(selectedImageUri);
                        // subtitle.setText(imagePath);
                    } catch (Exception e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            updateImage.setImageBitmap(photo);
            updateImage.setVisibility(View.VISIBLE);
            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri_camera(getApplicationContext(), photo);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            File finalFile = new File(getRealPathFromURI(tempUri));
            imagePath =  String.valueOf(finalFile);
            //System.out.println("This is the path" + finalFile);

        }
    }

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

    public void bottomNavSelection() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.profile:
                        Intent intent = new Intent(UpdateProductActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        Intent i = new Intent(UpdateProductActivity.this, HomeActivity.class);
                        startActivity(i);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.addPost:
                        Intent intent1 = new Intent(UpdateProductActivity.this, AddProductActivity.class);
                        startActivity(intent1);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

}