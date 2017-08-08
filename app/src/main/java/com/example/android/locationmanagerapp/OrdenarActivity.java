package com.example.android.locationmanagerapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemSelectedListener;


public class OrdenarActivity extends AppCompatActivity{
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenar);
        final String ID = getIntent().getStringExtra("RESTAURANT_ID");
        final String JSON = getIntent().getStringExtra("JSON_RESPUESTA");
        final String[] Nombres = getIntent().getStringArrayExtra("Names");
        final String[] OpcioneID = getIntent().getStringArrayExtra("IDs");



        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        //spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("12:00 p.m.");
        categories.add("12:30 p.m.");
        categories.add("01:00 p.m.");
        categories.add("01:30 p.m.");
        categories.add("02:00 p.m.");
        categories.add("02:30 p.m.");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        ArrayList<String> Nmbrs = new ArrayList<>(Arrays.asList(Nombres));
        lv = (ListView) findViewById(R.id.pedido);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                R.layout.text_item,R.id.textitem,
                Nmbrs );

        lv.setAdapter(arrayAdapter);


    }



}

