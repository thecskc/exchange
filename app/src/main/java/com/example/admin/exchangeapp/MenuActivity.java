package com.example.admin.exchangeapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;

import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

public class MenuActivity extends AppCompatActivity {

    ResideMenu resideMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        resideMenu = new ResideMenu(MenuActivity.this);
        resideMenu.attachToActivity(MenuActivity.this);

        // create menu items;
        String titles[] = { "Bids", "Services" };
        int icon[] = { R.drawable.ic_android_black_24dp, R.drawable.ic_android_black_24dp};


        ResideMenuItem servicesItem = new ResideMenuItem(this,icon[1],titles[1]);
        resideMenu.addMenuItem(servicesItem, ResideMenu.DIRECTION_LEFT);

        ResideMenuItem bidsItem = new ResideMenuItem(this,icon[0],titles[0]);
        resideMenu.addMenuItem(bidsItem, ResideMenu.DIRECTION_LEFT);




    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }
}
