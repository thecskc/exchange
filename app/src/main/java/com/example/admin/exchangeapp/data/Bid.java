package com.example.admin.exchangeapp.data;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

/**
 * Created by admin on 8/1/2018.
 */

public class Bid {
    private String postingUser, biddingUser, serviceID;
    double price;
    @ServerTimestamp
    private Date time;

    public Bid(String postingUser, String biddingUser, String serviceID, double price, Date time) {
        this.postingUser = postingUser;
        this.biddingUser = biddingUser;
        this.serviceID = serviceID;
        this.price = price;
        this.time = time;
    }

    public String getPostingUser() {
        return postingUser;
    }

    public void setPostingUser(String postingUser) {
        this.postingUser = postingUser;
    }

    public String getBiddingUser() {
        return biddingUser;
    }

    public void setBiddingUser(String biddingUser) {
        this.biddingUser = biddingUser;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
