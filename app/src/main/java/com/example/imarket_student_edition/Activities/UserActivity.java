package com.example.imarket_student_edition.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imarket_student_edition.DatabaseHelper.MyDatabase;
import com.example.imarket_student_edition.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserActivity extends AppCompatActivity {
    TextView uname;
    EditText user_update_name;
    String intentUser;

    BottomNavigationView bottomNavigationView;

    // Add my database helper
    MyDatabase database_helper = new MyDatabase(UserActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.user);

        uname = findViewById(R.id.u_name);
        user_update_name = findViewById(R.id.puser_name);
        get_current_user_info();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.user:
                        return true;

                    case R.id.home:
                        Intent intent = new Intent(UserActivity.this, ProductPageActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return  true;

                    case R.id.bookmark:
                        Intent i = new Intent(UserActivity.this, AddProductActivity.class);
                        startActivity(i);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    public void get_current_user_info(){
        Cursor cursor;
        cursor = database_helper.getData();
        if(cursor.getCount() == 0) {
            Toast.makeText(UserActivity.this, "No data found in database!", Toast.LENGTH_SHORT).show();

        }else if(cursor.getCount() >0){
            cursor.moveToFirst();
            user_update_name.setText(cursor.getString(1));
            // IF WE NEED TO USE USER ID JUST UNCOMMENT THE STATEMENT AND SET IT TO A VAIRABLE
            //user_id = Integer.parseInt(cursor.getString(2));
            uname.setText(cursor.getString(1));
        }
    }
}