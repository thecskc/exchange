package com.example.admin.exchangeapp.data;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

/**
 * Created by admin on 7/20/2018.
 */

public class Service {
    private String title, description, price, category, address, postingUser, city;
   // private boolean isFuture;
    @ServerTimestamp
    private Date dateTime;


    public String getPostingUser() {
        return postingUser;
    }

    public void setPostingUser(String postingUser) {
        this.postingUser = postingUser;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Service(String title, String description, String price, String category, String address,
                   String postingUser, String city, Date time) {

        this.title = title;
        this.description = description;
        this.price = price;
        this.category = category;
        this.address = address;
        this.postingUser = postingUser;
        this.city = city;
        this.dateTime = time;

    }

    public Service(String title, String description, String price) {
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public Service(){

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

//    public boolean isFuture() {
//        return isFuture;
//    }
//
//    public void setFuture(boolean future) {
//        isFuture = future;
//    }

    public Date getDateTime() {
        return dateTime;
    }
}
