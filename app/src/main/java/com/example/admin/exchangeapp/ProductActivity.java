package com.example.admin.exchangeapp;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductActivity extends AppCompatActivity {

    @BindView(R.id.sell_product_button)
    Button sellProductButton;

    @BindView(R.id.sell_product_title)
    EditText productTitle;

    @BindView(R.id.sell_product_description)
    EditText descriptionText;

    @BindView(R.id.sell_product_base_price)
    EditText basePrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ButterKnife.bind(ProductActivity.this);

        sellProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(productTitle.getText().toString().isEmpty() || descriptionText.getText().toString().isEmpty()
                        || basePrice.getText().toString().isEmpty()){
                    Toast.makeText(ProductActivity.this,"Fill out the form!", Toast.LENGTH_SHORT);
                }
                else
                {
                    String productTitleText = productTitle.getText().toString();
                    String productDescriptionText = descriptionText.getText().toString();
                    double basePriceText = Double.valueOf(basePrice.getText().toString());
                    Date currentTime = Calendar.getInstance().getTime();
                    String dateString = currentTime.toString();
                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    HashMap<String, Object> addData = new HashMap<>();
                    addData.put(Config.ProductCollection.PRODUCT_NAME,productTitleText);
                    addData.put(Config.ProductCollection.PRODUCT_DESCRIPTION,productDescriptionText);
                    addData.put(Config.ProductCollection.PRODUCT_BASE_PRICE,basePriceText);
                    addData.put(Config.ProductCollection.CURRENT_DATE_TIME, dateString);
                    addData.put(Config.ProductCollection.PRODUCT_POSTING_USER, FirebaseAuth.getInstance().getCurrentUser().getUid());

                    db.collection("Products").add(addData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(ProductActivity.this, "Success", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ProductActivity.this, "Failure", Toast.LENGTH_SHORT).show();

                                }
                            });

                }


            }
        });


    }
}
