package com.example.imarket_student_edition.DatabaseHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


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
    private static final String User_Column_DOB = "DOB";
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
                User_Column_DOB + " DATE," +
                User_Column_Password + " Password," +
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
}