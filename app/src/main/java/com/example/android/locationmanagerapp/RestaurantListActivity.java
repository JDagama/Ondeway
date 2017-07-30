package com.example.android.locationmanagerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class RestaurantListActivity extends AppCompatActivity {
    ArrayList<HashMap<String, String>> restaurantList;
    private String TAG = RestaurantListActivity.class.getSimpleName();
    private ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
        restaurantList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String jsonStr = intent.getStringExtra("JSON_RESPUESTA");

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);

            // Extrayendo el arreglo de restaurantes
            JSONArray Restaurants = jsonObj.getJSONArray("restaurants");

            // Barriendo todos los restaurantes
            for (int i = 0; i < Restaurants.length(); i++) {
                JSONObject c = Restaurants.getJSONObject(i);

                String id = c.getString("id");
                String commercial_name = c.getString("commercial_name");
                String slogan = c.getString("slogan");
                String full_address = c.getString("full_address");
                String working_hours=c.getString("working_hours");
                String first_price=c.getString("first_price");

                // Hash Temporal para un Restaurante
                HashMap<String, String> Restaurant = new HashMap<>();

                // agregando cada elemento al Hash temporal
                Restaurant.put("id", id);
                Restaurant.put("commercial_name", commercial_name);
                Restaurant.put("slogan", slogan);
                Restaurant.put("full_address", full_address);
                Restaurant.put("working_hours", working_hours);
                Restaurant.put("first_price", first_price);

                // agregando cada restaurante a la lista
                restaurantList.add(Restaurant);
            }
        } catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Json parsing error: " + e.getMessage(),
                            Toast.LENGTH_LONG)
                            .show();
                }
            });

        }

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
