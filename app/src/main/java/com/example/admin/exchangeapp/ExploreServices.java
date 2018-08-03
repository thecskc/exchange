package com.example.admin.exchangeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.admin.exchangeapp.data.Bid;
import com.example.admin.exchangeapp.data.Service;
import com.example.admin.exchangeapp.holders.ServiceHolder;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class ExploreServices extends AppCompatActivity {

    RecyclerView recyclerView;
    FirestoreRecyclerAdapter adapter;
    private double bidPrice;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_services);

        db = FirebaseFirestore.getInstance();

        final GridLayoutManager layoutManager = new GridLayoutManager(ExploreServices.this,GridLayoutManager.VERTICAL);

        recyclerView = findViewById(R.id.explore_recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(false);

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        Intent getIntent = this.getIntent();
        String cityName = getIntent.getStringExtra("cityName");
        Toast.makeText(ExploreServices.this, cityName, Toast.LENGTH_SHORT).show();


        Query servicesInCity = db.collection(Config.ServiceCollection.COLLECTION_NAME).whereEqualTo("city", cityName);

        FirestoreRecyclerOptions<Service> options = new FirestoreRecyclerOptions.Builder<Service>()
                .setQuery(servicesInCity, Service.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Service, ServiceHolder>(options) {
            @Override
            public void onBindViewHolder(@NonNull ServiceHolder holder, int position, @NonNull final Service model) {
                holder.titleTv.setText(model.getTitle());
                holder.descTv.setText(model.getDescription());
                final String setPrice = "$"+model.getPrice();
                holder.priceTV.setText(setPrice);
                holder.serviceIv.setVisibility(View.GONE);
                holder.serviceCv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new MaterialDialog.Builder(ExploreServices.this)
                                .title(R.string.input)
                                .content(R.string.input_content)
                                .inputType(InputType.TYPE_CLASS_NUMBER)
                                .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                                    @Override
                                    public void onInput(MaterialDialog dialog, CharSequence input) {
                                        // Do something
                                        bidPrice = Double.parseDouble(input.toString().substring(1, input.length()));
                                        final String biddingUser = FirebaseAuth.getInstance()
                                                .getCurrentUser().getUid();

                                        CollectionReference servicesRef = db.collection(Config.ServiceCollection
                                                .COLLECTION_NAME);

                                        db.collection(Config.ServiceCollection.COLLECTION_NAME)
                                                .whereEqualTo("postingUser", model.getPostingUser())
                                                .whereEqualTo("time", model.getDateTime())
                                                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                            @Override
                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                String serviceID = queryDocumentSnapshots.getDocuments().get(0).getId();
                                                Bid bid = new Bid(model.getPostingUser(),
                                                        biddingUser, serviceID, bidPrice, null);

                                                db.collection("Bids").add(bid)
                                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                            @Override
                                                            public void onSuccess(DocumentReference documentReference) {
                                                                Toast.makeText(getApplicationContext(),
                                                                        "Success", Toast.LENGTH_SHORT)
                                                                        .show();
                                                            }
                                                        });

                                            }
                                        });


                                    }
                                }).show();
                    }
                });

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
