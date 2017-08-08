package com.example.android.locationmanagerapp;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Daniel on 30/07/2017.
 */

public class JsonHandler {
    public JsonHandler() {
    }

    public ArrayList<HashMap<String, String>> getRestaurantList(String JSONString){
        ArrayList<HashMap<String, String>> restaurantList = new ArrayList<>();

        try {

            JSONObject jsonObj = new JSONObject(JSONString);

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
                String first_price="S/. "+c.getString("first_price");

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
            Log.e("JSON Handler", "Json parsing error: " + e.getMessage());
            return null;
        }
        return restaurantList;
    }

    public HashMap<String, String> getSpecificRestaurant(String JSONString, String ID){
        HashMap<String, String> Restaurant = new HashMap<>();

        try {

            JSONObject jsonObj = new JSONObject(JSONString);
            // Extrayendo el arreglo de restaurantes
            JSONArray Restaurants = jsonObj.getJSONArray("restaurants");

            // Barriendo todos los restaurantes
            for (int i = 0; i < Restaurants.length(); i++) {
                JSONObject c = Restaurants.getJSONObject(i);

                    if(c.getString("id").equals(ID)){
                        //Si este restaurant tiene el ID que buscamos, sacar sus datos
                        Restaurant.put("id", c.getString("id"));
                        Restaurant.put("commercial_name", c.getString("commercial_name"));
                        Restaurant.put("slogan", c.getString("slogan"));
                        Restaurant.put("full_address", c.getString("full_address"));
                        Restaurant.put("working_hours", c.getString("working_hours"));
                        Restaurant.put("first_price", "S/. "+c.getString("first_price"));
                        Restaurant.put("menu_title",c.getJSONObject("first_menu").getString("type_name"));
                    }
            }

        } catch (final JSONException e) {
            Log.e("xxx JSON Handler", "Json parsing error: " + e.getMessage());
            return null;
        }
        return Restaurant;
    }



}
