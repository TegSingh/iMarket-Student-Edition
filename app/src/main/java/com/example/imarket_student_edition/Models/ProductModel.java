package com.example.imarket_student_edition.Models;

import java.util.Date;

public class ProductModel {
    private int id;
    private String name;
    private String img_video_url;
    private String description;
    private String date_added;
    private String price;
    private String phone_number;
    private String location;
    // This is the foreign key to the User table forming a one-to-many relationship
    private int user_id;

    // Define the constructor
    public ProductModel(int id, String name, String img_video_url, String description, String date_added, String price, int user_id, String phone_number, String location) {
        this.id = id;
        this.name = name;
        this.img_video_url = img_video_url;
        this.description = description;
        this.date_added = date_added;
        this.price = price;
        this.user_id = user_id;
        this.phone_number = phone_number;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_video_url() {
        return img_video_url;
    }

    public void setImg_video_url(String img_video_url) {
        this.img_video_url = img_video_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // Overwrite the Tostring method
    @Override
    public String toString() {
        return "Product ID: " + this.id + " Name: " + this.name + " Price: " + this.price + " User ID: " + this.user_id;
    }
}
