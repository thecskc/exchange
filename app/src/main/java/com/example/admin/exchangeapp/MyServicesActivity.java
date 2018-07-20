package com.example.admin.exchangeapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.admin.exchangeapp.adapters.MyServicesAdapter;
import com.example.admin.exchangeapp.data.Service;

import java.util.ArrayList;

public class MyServicesActivity extends AppCompatActivity implements MyServicesAdapter.OnListItemClickListener {
    private RecyclerView recyclerView;
    private MyServicesAdapter mAdapter;

    private ArrayList<Service> mServices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_services);

        mServices = new ArrayList<>();
        mServices.add(new Service("Some Service", "Some description", ""));
        mServices.add(new Service("Some Service", "Some description", ""));
        mServices.add(new Service("Some Service", "Some description", ""));
        mServices.add(new Service("Some Service", "Some description", ""));
        mServices.add(new Service("Some Service", "Some description", ""));
        mServices.add(new Service("Some Service", "Some description", ""));
        mServices.add(new Service("Some Service", "Some description", ""));
        mServices.add(new Service("Some Service", "Some description", ""));
        mServices.add(new Service("Some Service", "Some description", ""));

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyServicesAdapter(this, mServices, this);

        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onListItemClick(int position) {

        Toast.makeText(this, "Clicked at position: "+(position+1), Toast.LENGTH_SHORT)
                            .show();
    }
}
