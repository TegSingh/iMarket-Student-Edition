package com.example.imarket_student_edition.DatabaseHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.imarket_student_edition.Models.ProductModel;
import com.example.imarket_student_edition.Models.UserModel;

import java.util.ArrayList;


public class MyDatabase  extends SQLiteOpenHelper {

    private Context context;

    // Create constants and database parameters
    private static final String DATABASE_NAME = "iMarket.db";
    private static final int DATABASE_VERSION = 1;

    // Define column names in Table user
    private static final String User_Table = "User";
    private static final String User_Column_ID = "ID";
    private static final String User_Column_Name = "Name";
    private static final String User_Column_Email = "Email";
    private static final String User_Column_Password = "Password";
    private static final String User_Column_Location = "Location";
    private static final String User_Column_DateCreated = "DateCreated";

    // Define column names in Table Product
    private static final String Product_Table = "Product";
    private static final String Product_Column_ID = "ID";
    private static final String Product_Column_Name = "Name";
    private static final String Product_Column_Image_video = "imgVideo";
    private static final String Product_Column_Description = "Description";
    private static final String Product_Column_DateAdded = "DateAdded";
    private static final String Product_Column_Price = "Price";
    private static final String Product_Column_UserID = "UserId";


    // Define the constructor
    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        System.out.println("On create event handler called from DATABASE HELPER");
        // Query to create User table
        String query = "CREATE TABLE " + User_Table +
                " (" + User_Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                User_Column_Name + " TEXT," +
                User_Column_Email  + " TEXT," +
                User_Column_Password + " PASSWORD," +
                User_Column_Location + " Text," +
                User_Column_DateCreated + " DATE);";

        db.execSQL(query);

        // Query to create Product table
        String query2= "CREATE TABLE " + Product_Table +
                " (" + Product_Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Product_Column_Name + " TEXT," +
                Product_Column_Image_video  + " TEXT," +
                Product_Column_Description + " TEXT," +
                Product_Column_DateAdded + " DATE," +
                Product_Column_Price + " DOUBLE," +
                Product_Column_UserID + " INTEGER);";

        db.execSQL(query2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + User_Table);
        db.execSQL("DROP TABLE IF EXISTS " + Product_Table);
        onCreate(db);
    }

    // Method to authenticate user for login
    public boolean authenticateUser(String email, String password) {
        System.out.println("Authenticate User method called from database helper");
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + User_Table + " WHERE " + User_Column_Email + "=\'" + email + "\' AND " + User_Column_Password + "=\'" + password + "\'";
        Cursor cursor = db.rawQuery(query, null);

        // Get the number of users with the given email and password
        int number_of_users = cursor.getCount();
        System.out.println("Number of Users fetched: " + number_of_users);

        if (number_of_users == 1) {
            cursor.moveToFirst();
            // Get user name which is the second column
            String fetched_user_name = cursor.getString(1);
            System.out.println("User found: Name: " + fetched_user_name + " authenticated");

            // Close the cursor and database object
            cursor.close();
            db.close();
            return true;

        } else if (number_of_users == 0) {
            System.out.println("Database Helper: User email not found");
            return false;
        } else {
            // THE CODE SHOULD NOT REACH THIS ELSE STATEMENT AFTER CORRECT EMAIL VALIDATION IN REGISTRATION
            return false;
        }
    }

    // Method to get a list of all users
    public ArrayList<UserModel> get_all_users() {
        ArrayList<UserModel> user_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + User_Table;
        Cursor cur = db.rawQuery(query, null);

        // While cursor has an element to move to
        if(cur.moveToNext()) {
            do {
                int id = cur.getInt(0);
                String name = cur.getString(1);
                String email = cur.getString(2);
                String password = cur.getString(3);
                String location = cur.getString(4);
                String date_created = cur.getString(5);

                //make note and add to list
                UserModel user = new UserModel(id, name, email, password, location, date_created);

                // Print the user information
                System.out.println();
                user_list.add(user);
            } while(cur.moveToNext());
        }

        db.close();
        cur.close();
        return user_list;
    }

    // Method  to update a user in the table
    public boolean update_user(int id, UserModel user) {
        System.out.println("Update the user with id: " + id);
        return false;
    }

    // Method  to update a user in the product table
    public boolean update_product(int id, ProductModel product) {
        System.out.println("Update the Product with id: " + id);
        return false;
    }

    // Method to add user to the table
    public boolean insert_user(UserModel user) {
        System.out.println("Insert user method called from Database Helper");

        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        // Add values into the content values structure
        cv.put(User_Column_ID, user.getId());
        cv.put(User_Column_Name, user.getName());
        cv.put(User_Column_Email, user.getEmail());
        cv.put(User_Column_Password, user.getPassword());
        cv.put(User_Column_Location, user.getLocation());
        cv.put(User_Column_DateCreated, user.getDate_created());

        // Add the content values to the database
        long result = db.insert(User_Table, null, cv);

        if (result == -1) {
            // Insert was unsuccessful
            System.out.println("Database Helper: User Insert not successful");
            return false;
        } else {
            System.out.println("Database Helper: User Insert Successful");
            return true;
        }
    }

    // Method to add Product to the table
    public boolean insert_product(ProductModel product) {
        System.out.println("Insert product method called from Database Helper");
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        // Add values into the content values structure
        cv.put(Product_Column_ID,  product.getId());
        cv.put(Product_Column_Name, product.getName());
        cv.put(Product_Column_Image_video, product.getImg_video_url());
        cv.put(Product_Column_Description, product.getDescription());
        cv.put(Product_Column_DateAdded, product.getDate_added());
        cv.put(Product_Column_Price, product.getPrice());
        cv.put(Product_Column_UserID, product.getUser_id());

        // Add the content values to the database
        long result = db.insert(Product_Table, null, cv);
        if (result == -1) {
            // Insert was unsuccessful
            System.out.println("Database Helper: Product Insert not successful");
            return false;
        } else {
            System.out.println("Database Helper: Product Insert Successful");
            return true;
        }

    }

}