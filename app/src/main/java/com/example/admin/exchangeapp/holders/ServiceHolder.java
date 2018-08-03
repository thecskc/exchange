package com.example.admin.exchangeapp.holders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.exchangeapp.R;

public class ServiceHolder extends RecyclerView.ViewHolder{
    public TextView titleTv, descTv;
    public ImageView serviceIv;
    public CardView serviceCv;
    public TextView priceTV;


    public ServiceHolder(View itemView) {
        super(itemView);
        titleTv = itemView.findViewById(R.id.title);
        descTv = itemView.findViewById(R.id.desc);
        serviceIv = itemView.findViewById(R.id.image_view);
        serviceCv = itemView.findViewById(R.id.service_cardView);
        priceTV = itemView.findViewById(R.id.priceTV);
    }
}
