package com.example.admin.exchangeapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.exchangeapp.adapters.MyServicesAdapter;
import com.example.admin.exchangeapp.data.Service;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MyBidsActivity extends AppCompatActivity implements MyServicesAdapter.OnListItemClickListener {
    private RecyclerView recyclerView;

    private  MyServicesAdapter mAdapter;
    private ArrayList<Service> mServices;
    private FirebaseFirestore db;
    private String user;
    private ArrayList<String> serviceIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_services);

        setTitle("My Bids");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        serviceIds = new ArrayList<>();
        mServices = new ArrayList<>();
        user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();

        Query myBids =  db.collection("Bids")
                .whereEqualTo("biddingUser", user);

        myBids.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for(QueryDocumentSnapshot snapshot: queryDocumentSnapshots){
                    serviceIds.add(snapshot.get("serviceID").toString());
                }

                for (int i=0; i<serviceIds.size(); i++){
                    db.collection(Config.ServiceCollection.COLLECTION_NAME).document(serviceIds.get(i))
                            .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Service service = documentSnapshot.toObject(Service.class);
                            mServices.add(service);
                            Log.d("Bids", "onSuccess: "+service.getTitle());
                        }
                    });
                }

                mAdapter = new MyServicesAdapter(MyBidsActivity.this,mServices,
                        MyBidsActivity.this);
                mAdapter.notifyDataSetChanged();


            }

        });
        recyclerView.setAdapter(mAdapter);




    }

    @Override
    public void onListItemClick(int position) {
        Toast.makeText(this, "Clicked at position: "+(position+1), Toast.LENGTH_SHORT)
                .show();
    }
}
