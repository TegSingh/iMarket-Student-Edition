package com.example.imarket_student_edition.DatabaseHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.imarket_student_edition.Models.ProductModel;
import com.example.imarket_student_edition.Models.UserModel;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class MyDatabase  extends SQLiteOpenHelper {

    private Context context;
    public String user_name, user_id;

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
    private static final String Product_Column_UserID = "UserID";


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
                Product_Column_Price + " Text," +
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


    //Method to get use ID and Name
    public String userName(String email) {
       // System.out.println("Authenticate User method called from database helper");
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + User_Table + " WHERE " + User_Column_Email + "=\'" + email + "\'";
        Cursor cursor = db.rawQuery(query, null);

        // Get the number of users with the given email and password
        int number_of_users = cursor.getCount();
        System.out.println("Number of Users fetched: " + number_of_users);

        if (number_of_users == 1) {
            cursor.moveToFirst();
            // Get user name which is the second column
            user_name = cursor.getString(1);
            user_id = cursor.getString(0);
            System.out.println("User found: Name: for product page " + user_name);
            // Close the cursor and database object
            cursor.close();
            db.close();
            return user_name;

        } else if (number_of_users == 0) {
            System.out.println("Database Helper: User email not found");
            return null;
        } else {
            // THE CODE SHOULD NOT REACH THIS ELSE STATEMENT AFTER CORRECT EMAIL VALIDATION IN REGISTRATION
            return null;
        }
    }
    public String userID(){
        if(user_name.length() != 0){
            return user_id;
        }
        return null;
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

    // Method to get all products
    public ArrayList<ProductModel> get_all_products() {
        System.out.println("Database helper: Method to get all products called");
        ArrayList<ProductModel> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + Product_Table;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String image_video = cursor.getString(2);
                String description = cursor.getString(3);
                String date_created = cursor.getString(4);
                String price = cursor.getString(5);
                int user_id = cursor.getInt(6);

                ProductModel product = new ProductModel(id, name, image_video, description, date_created, price, user_id);
                System.out.println("Product: " + product.toString());
                products.add(product);
            } while (cursor.moveToNext());
        }

        return products;
    }

    // Method  to update a user in the table
    public boolean update_user(UserModel user, UserModel user_updated) {
        System.out.println("Database helper: Update the user with id: ");
        System.out.println("Previous User: " + user.toString());
        System.out.println("Updated User: " + user_updated.toString());

        SQLiteDatabase db = this.getWritableDatabase();

        // Content values to be used as update argument
        ContentValues cv = new ContentValues();
        cv.put("id", user_updated.getId());
        cv.put("name", user_updated.getName());
        cv.put("email", user_updated.getEmail());
        cv.put("password", user_updated.getPassword());
        cv.put("location", user_updated.getLocation());
        cv.put("data_created", user_updated.getDate_created());

        String whereClause = "id = ?";
        Integer id = new Integer(user_updated.getId());

        String whereArgs[] = {id.toString()};
        int result = db.update(User_Column_Name, cv, whereClause, whereArgs);
        if (result == 1) {
            System.out.println("Update successful User");
            return true;
        } else {
            System.out.println("Could not update user");
            return false;
        }
    }

    // Method  to update a user in the product table
    public boolean update_product(ProductModel product, ProductModel product_updated) {
        System.out.println("Database helper: Update the Product");
        System.out.println("Previous location: " + product.toString());
        System.out.println("Updated location: " + product_updated.toString());
        SQLiteDatabase db = this.getWritableDatabase();

        // Content values to be used as update argument
        ContentValues cv = new ContentValues();
        cv.put("id", product_updated.getId());
        cv.put("name", product_updated.getName());
        cv.put("image_video", product_updated.getImg_video_url());
        cv.put("description", product_updated.getDescription());
        cv.put("date_added", product_updated.getDate_added());
        cv.put("price", product_updated.getPrice());
        cv.put("user_id", product_updated.getUser_id());

        String whereClause = "id = ?";
        Integer id = new Integer(product_updated.getId());

        String whereArgs[] = {id.toString()};
        int result = db.update(Product_Column_Name, cv, whereClause, whereArgs);
        if (result == 1) {
            System.out.println("Database helper: Update successful Product");
            return true;
        } else {
            System.out.println("Database helper: Could not update Product");
            return false;
        }
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

    // Method to delete a product from the table
    public boolean delete_user(int id) {

        System.out.println("Database helper: Delete user method called");
        // Define the database
        SQLiteDatabase db = this.getWritableDatabase();

        Integer col_id = id;
        String[] args = {col_id.toString()};
        int num_rows_deleted = 0;
        try {
            num_rows_deleted = db.delete(User_Table, User_Column_ID + " = ? ", args);
        } catch (Exception e) {
            System.out.println("Database helper: Error executing delete query for user");
            db.close();
            return false;
        }

        if (num_rows_deleted == 1) {
            System.out.println("Database helper: User row deleted successfully");
            db.close();
            return true;
        } else if (num_rows_deleted == 0) {
            System.out.println("Database helper: Error deleting user, Row not found");
            db.close();
            return false;
        } else {
            System.out.println("Database helper: Error deleting User: too many rows deleted: " + num_rows_deleted);
            db.close();
            return true;
        }
    }

    // Method to delete a user from the table
    public boolean delete_product(int id) {
        System.out.println("Database helper: Delete Product method called");
        // Define the database
        SQLiteDatabase db = this.getWritableDatabase();

        Integer col_id = id;
        String[] args = {col_id.toString()};
        int num_rows_deleted = 0;
        try {
            num_rows_deleted = db.delete(Product_Table, Product_Column_ID + " = ? ", args);
        } catch (Exception e) {
            System.out.println("Database helper: Error executing delete query for product");
            db.close();
            return false;
        }

        if (num_rows_deleted == 1) {
            System.out.println("Database helper: Product row deleted successfully");
            db.close();
            return true;
        } else if (num_rows_deleted == 0) {
            System.out.println("Database helper: Error deleting Product, Row not found");
            db.close();
            return false;
        } else {
            System.out.println("Database helper: Error deleting Product, too many rows deleted: " + num_rows_deleted);
            db.close();
            return true;
        }
    }

    // Method to search product by product name
    public ArrayList<ProductModel> search_products(String name) {
    // Works
        System.out.println("Database helper: Method to search product called");
        ArrayList<ProductModel> filtered_products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + Product_Table + " WHERE " + Product_Column_Name + " LIKE \'%" + name + "%\'";
        Cursor cursor = db.rawQuery(query, null);
        System.out.println("Number of products fetched: " + cursor.getCount());

        if (cursor.moveToFirst()) {
            do {
                // Get the variable names from the cursor
                int id = cursor.getInt(0);
                String product_name = cursor.getString(1);
                String image_video = cursor.getString(2);
                String description = cursor.getString(3);
                String product_date_created = cursor.getString(4);
                String price = cursor.getString(5);
                int user_id = cursor.getInt(6);
                filtered_products.add(new ProductModel(id, product_name, image_video, description, product_date_created, price, user_id));

            } while (cursor.moveToNext());
        }

        return filtered_products;
    }

}