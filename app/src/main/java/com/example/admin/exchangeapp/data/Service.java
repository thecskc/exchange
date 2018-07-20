package com.example.admin.exchangeapp.data;

/**
 * Created by admin on 7/20/2018.
 */

public class Service {
    private String mTitle, mDescription, mImageUrl;

    public Service(String title, String description, String imageUrl) {
        mTitle = title;
        mDescription = description;
        mImageUrl = imageUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}
