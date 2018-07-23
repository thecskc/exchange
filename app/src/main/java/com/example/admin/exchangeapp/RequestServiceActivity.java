package com.example.admin.exchangeapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RequestServiceActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.title)
    EditText mTitle;
    @BindView(R.id.desc)
    EditText mDesc;
    @BindView(R.id.price)
    EditText mPrice;
    @BindView(R.id.category)
    EditText mCategory;
    @BindView(R.id.deadline_spinner)
    AppCompatSpinner mDeadlineSpinner;
    @BindView(R.id.submit_btn)
    Button mSubmitBtn;
    @BindView(R.id.service_photo)
    ImageButton mServicePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_service);
        ButterKnife.bind(this);

        Calendar now = Calendar.getInstance();
        final DatePickerDialog dpd = DatePickerDialog.newInstance(
                RequestServiceActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );        String[] spinnerChoices = {"real-time", "future date"};
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.deadline_spinner_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mDeadlineSpinner.setAdapter(adapter);

        mDeadlineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {

                if(pos == 1){
                    dpd.show(getFragmentManager(), "Datepickerdialog");
                }
                else{

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "You picked the following date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        Toast.makeText(this, "Date: "+date , Toast.LENGTH_SHORT).show();
    }
}
