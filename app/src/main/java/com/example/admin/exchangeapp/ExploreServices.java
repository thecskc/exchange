package com.example.admin.exchangeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.example.admin.exchangeapp.data.Service;
import com.example.admin.exchangeapp.holders.ServiceHolder;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ExploreServices extends AppCompatActivity {

    RecyclerView recyclerView;
    FirestoreRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_services);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(ExploreServices.this);

        recyclerView = (RecyclerView)findViewById(R.id.explore_recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(false);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Intent getIntent = this.getIntent();
        String cityName = getIntent.getStringExtra("cityName");
        Toast.makeText(ExploreServices.this,cityName,Toast.LENGTH_SHORT).show();


        Query servicesInCity = db.collection(Config.ServiceCollection.COLLECTION_NAME).whereEqualTo("city", cityName);

        FirestoreRecyclerOptions<Service> options = new FirestoreRecyclerOptions.Builder<Service>()
                .setQuery(servicesInCity,Service.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Service, ServiceHolder>(options) {
            @Override
            public void onBindViewHolder(@NonNull ServiceHolder holder, int position, @NonNull Service model) {

                holder.titleTv.setText(model.getTitle());
                holder.descTv.setText(model.getDescription());
                holder.serviceIv.setImageResource(R.drawable.ic_android_black_24dp);

            }

            @Override
            public ServiceHolder onCreateViewHolder(ViewGroup group, int i) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.item_services, group, false);

                return new ServiceHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);








    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
