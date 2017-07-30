package com.example.android.locationmanagerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class RestaurantListActivity extends AppCompatActivity {
    ArrayList<HashMap<String, String>> restaurantList;
    private String TAG = RestaurantListActivity.class.getSimpleName();
    private ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        lv = (ListView) findViewById(R.id.list);
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String jsonStr = intent.getStringExtra("JSON_RESPUESTA");

        //Extrayendo la lista de restaurantes del JSON
        JsonHandler jh = new JsonHandler();
        restaurantList= jh.getRestaurantList(jsonStr);


        //rellenando las vistas con lo obtenido del JSON
        ListAdapter adapter = new SimpleAdapter(
                RestaurantListActivity.this,
                restaurantList,
                R.layout.list_item,
                new String[]{"id", "commercial_name", "slogan","full_address","working_hours","first_price"},
                new int[]{R.id.id, R.id.commercial_name, R.id.slogan, R.id.full_address, R.id.working_hours, R.id.first_price}
        );
        lv.setAdapter(adapter);


    }
}
