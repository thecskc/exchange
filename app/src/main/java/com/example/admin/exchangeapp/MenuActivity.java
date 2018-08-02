package com.example.admin.exchangeapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MenuActivity extends AppCompatActivity {

    ResideMenu resideMenu;
    private Button productBtn;
    FloatingActionButton serviceBtn;
    EditText searchAddress;
    Button searchButton;
    ImageView randomImage;

    CoordinatorLayout bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        serviceBtn = (FloatingActionButton)findViewById(R.id.service_btn);

        resideMenu = new ResideMenu(MenuActivity.this);
        resideMenu.attachToActivity(MenuActivity.this);
        resideMenu.setBackground(R.drawable.bg1);
        searchAddress = (EditText)findViewById(R.id.searchAddress);
        searchButton = (Button)findViewById(R.id.button3);

        randomImage = (ImageView)findViewById(R.id.imageView3);

        Picasso.get().load(Config.IMAGE_URL).placeholder(R.drawable.logo_resized).fit().centerCrop().into(randomImage);




        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient client = new AsyncHttpClient(); //use this for Geocoding later
                String address = searchAddress.getText().toString();
                String getUrl = Config.GEOCODING_URL + address + "&key=" + Config.API_KEY;
                client.get(getUrl, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        JSONObject root = null;
                        try {
                            root = new JSONObject(responseString);
                            String city = root.getJSONArray("results").getJSONObject(0)
                                    .getJSONArray("address_components").getJSONObject(3).getString("short_name");
                            Intent intent = new Intent(MenuActivity.this,ExploreServices.class);
                            intent.putExtra("cityName",city);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
            }
        });


        // create menu items;
        String titles[] = { "Bids", "Services", "My Profile" };
        int icon[] = { R.drawable.ic_android_black_24dp, R.drawable.ic_android_black_24dp,
                        R.drawable.ic_android_black_24dp};


        ResideMenuItem servicesItem = new ResideMenuItem(this,icon[1],titles[1]);
        resideMenu.addMenuItem(servicesItem, ResideMenu.DIRECTION_LEFT);
        servicesItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, MyServicesActivity.class));
            }
        });

        ResideMenuItem bidsItem = new ResideMenuItem(this,icon[0],titles[0]);
        resideMenu.addMenuItem(bidsItem, ResideMenu.DIRECTION_LEFT);
        bidsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, MyBidsActivity.class));
            }
        });

        ResideMenuItem profileItem = new ResideMenuItem(this,icon[0],titles[2]);
        resideMenu.addMenuItem(profileItem, ResideMenu.DIRECTION_LEFT);
        profileItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, MyProfileActivity.class));
            }
        });

        serviceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, RequestServiceActivity.class));
            }
        });






    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }
}
