package com.example.admin.exchangeapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.admin.exchangeapp.adapters.MyServicesAdapter;
import com.example.admin.exchangeapp.data.Service;
import com.example.admin.exchangeapp.holders.ServiceHolder;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class MyServicesActivity extends AppCompatActivity implements MyServicesAdapter.OnListItemClickListener {
    private RecyclerView recyclerView;
    //private MyServicesAdapter mAdapter;
    private FirestoreRecyclerAdapter mAdapter;
    private FirebaseFirestore db;
    private String user;

    private ArrayList<Service> mServices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_services);

        setTitle("My Services");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();

//        mServices = new ArrayList<>();
//        mServices.add(new Service("Some Service", "Some description", ""));
//        mServices.add(new Service("Some Service", "Some description", ""));
//        mServices.add(new Service("Some Service", "Some description", ""));
//        mServices.add(new Service("Some Service", "Some description", ""));
//        mServices.add(new Service("Some Service", "Some description", ""));
//        mServices.add(new Service("Some Service", "Some description", ""));
//        mServices.add(new Service("Some Service", "Some description", ""));
//        mServices.add(new Service("Some Service", "Some description", ""));
//        mServices.add(new Service("Some Service", "Some description", ""));

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mAdapter = new MyServicesAdapter(this, mServices, this);

        Query myServices =  db.collection(Config.ServiceCollection.COLLECTION_NAME)
                            .whereEqualTo("postingUser", user);

        FirestoreRecyclerOptions<Service> options = new FirestoreRecyclerOptions.Builder<Service>()
                .setQuery(myServices,Service.class)
                .build();

        mAdapter = new FirestoreRecyclerAdapter<Service, ServiceHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ServiceHolder holder, int position, @NonNull Service model) {
                holder.descTv.setText(model.getDescription());
                holder.titleTv.setText(model.getTitle());
                holder.serviceIv.setImageResource(R.drawable.ic_android_black_24dp);
            }

            @NonNull
            @Override
            public ServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_services, parent, false);
                return new ServiceHolder(view);
            }
        };

        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onListItemClick(int position) {

        Toast.makeText(this, "Clicked at position: "+(position+1), Toast.LENGTH_SHORT)
                            .show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }
}
