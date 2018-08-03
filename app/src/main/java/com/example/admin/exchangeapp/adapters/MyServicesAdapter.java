package com.example.admin.exchangeapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.exchangeapp.R;
import com.example.admin.exchangeapp.data.Service;

import java.util.ArrayList;

/**
 * Created by admin on 7/20/2018.
 */

public class  MyServicesAdapter extends RecyclerView.Adapter<MyServicesAdapter.ServicesHolder> {

    private Context mContext;
    private ArrayList<Service> mServices = new ArrayList<>();
    private OnListItemClickListener mOnListItemClickListener;
    public MyServicesAdapter(Context context, ArrayList<Service> services, OnListItemClickListener listener){
        mContext = context;
        mServices = services;
        mOnListItemClickListener = listener;
    }
    public MyServicesAdapter(){}
    @NonNull
    @Override
    public MyServicesAdapter.ServicesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_services, parent, false);
        return new ServicesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyServicesAdapter.ServicesHolder holder, int position) {
        holder.titleTv.setText(mServices.get(position).getTitle());
        holder.descTv.setText(mServices.get(position).getDescription());
        holder.serviceIv.setImageResource(R.drawable.ic_android_black_24dp);
        holder.priceTv.setText(mServices.get(position).getPrice()+"$");
        holder.serviceIv.setVisibility(View.GONE);



    }

    @Override
    public int getItemCount() {
        return mServices.size();
    }

    class ServicesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleTv, descTv, priceTv;
        ImageView serviceIv;
        public ServicesHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            titleTv = itemView.findViewById(R.id.title);
            descTv = itemView.findViewById(R.id.desc);
            priceTv = itemView.findViewById(R.id.priceTV);
            serviceIv = itemView.findViewById(R.id.image_view);

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mOnListItemClickListener.onListItemClick(position);
        }
    }
    public interface OnListItemClickListener {
        public void onListItemClick(int position);
    }

    public void addItem(Service service, String price){
        service.setPrice(price);
        mServices.add(service);
        notifyDataSetChanged();
    }
}

