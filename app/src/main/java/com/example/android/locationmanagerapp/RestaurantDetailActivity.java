package com.example.android.locationmanagerapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class RestaurantDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        String ID = getIntent().getStringExtra("RESTAURANT_ID");
        String JSON = getIntent().getStringExtra("JSON_RESPUESTA");

        EditText editID = (EditText)findViewById(R.id.textotemporal);
        editID.setText(ID);
        EditText editJSON = (EditText)findViewById(R.id.jsontemporal);
        editJSON.setText(JSON);
    }
}
