package com.example.admin.exchangeapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.admin.exchangeapp.data.Service;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;


public class RequestServiceActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    @BindView(R.id.title)
    EditText mTitle;
    @BindView(R.id.desc)
    EditText mDesc;
    @BindView(R.id.price)
    EditText mPrice;
    @BindView(R.id.category)
    EditText mCategory;

    @BindView(R.id.submit_btn)
    Button submitButton;
    @BindView(R.id.service_photo)
    ImageButton mServicePhoto;

    @BindView(R.id.address)
    EditText addressET;


    boolean isFuture;
    String title, description, price, category, address, city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_service);
        ButterKnife.bind(this);

        Logger.addLogAdapter(new AndroidLogAdapter());

        Calendar now = Calendar.getInstance();
        final DatePickerDialog dpd = DatePickerDialog.newInstance(
                RequestServiceActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        String[] spinnerChoices = {"Real Time", "Future Date"};
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.deadline_spinner_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
//        mDeadlineSpinner.setAdapter(adapter);
//
//        mDeadlineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
//
//                if (pos == 1) {
//                    dpd.show(getFragmentManager(), "Datepickerdialog");
//                    isFuture = true;
//                } else {
//                    isFuture = false;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = mTitle.getText().toString();
                description = mDesc.getText().toString();
                price = mPrice.getText().toString();
                category = mCategory.getText().toString();
                address = addressET.getText().toString();
                city = address;

                //TODO: Geocoding - convert address to lat and lon
                AsyncHttpClient client = new AsyncHttpClient(); //use this for Geocoding later
                String getUrl = Config.GEOCODING_URL + address + "&key=" + Config.API_KEY;
                Logger.e(getUrl);

                client.get(getUrl, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        try {
                            JSONObject root = new JSONObject(responseString);
                            city = root.getJSONArray("results").getJSONObject(0)
                                    .getJSONArray("address_components")
                                    .getJSONObject(3).getString("short_name");

                            Logger.e("here - " + city);

                            String postingUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

                            Service service = new Service(title, description, price, category,
                                                address, postingUser, city, null);
                            Logger.e("outside - " + city);
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
//                            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
//                                    .setTimestampsInSnapshotsEnabled(true)
//                                    .build();
//                            db.setFirestoreSettings(settings);

                            db.collection(Config.ServiceCollection.COLLECTION_NAME).add(service)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(RequestServiceActivity.this, "Success", Toast.LENGTH_SHORT).show();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RequestServiceActivity.this, "Failure", Toast.LENGTH_SHORT).show();

                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Logger.e("exception");
                        }

                    }
                });


            }
        });
    }



    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "You picked the following date: " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        Toast.makeText(this, "Date: " + date, Toast.LENGTH_SHORT).show();
    }


}
