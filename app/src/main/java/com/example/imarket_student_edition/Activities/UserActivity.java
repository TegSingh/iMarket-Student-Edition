package com.example.imarket_student_edition.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.imarket_student_edition.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserActivity extends AppCompatActivity {
    TextView uname;
    EditText user_update_name;
    String intentUser;

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.user);

        uname = findViewById(R.id.u_name);
        user_update_name = findViewById(R.id.puser_name);

        if(getIntent().hasExtra("UserName")) {
            intentUser = getIntent().getStringExtra("UserName");

            uname.setText(intentUser);
            user_update_name.setText(intentUser);
        }else {
            uname.setText("No user detected");
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.user:
                        return true;

                    case R.id.home:
                        Intent intent = new Intent(UserActivity.this, ProductPageActivity.class);
                        intent.putExtra("UserName",intentUser);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return  true;

                    case R.id.bookmark:
                        Intent i = new Intent(UserActivity.this, AddProductActivity.class);
                        i.putExtra("UserName",intentUser);
                        startActivity(i);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}