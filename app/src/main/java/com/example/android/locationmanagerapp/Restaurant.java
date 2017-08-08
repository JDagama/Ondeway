package com.example.android.locationmanagerapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Daniel on 5/08/2017.
 */

public class Restaurant {
    private String id;
    private String commercial_name;
    private String slogan;
    private String full_address;
    private String working_hours;
    private String first_price;
    private String menutitle;
    private String[] submenulist;
    private HashMap<String,String[]> submenuNames = new HashMap<>();
    private HashMap<String,HashMap<String,String>> submenuIDs = new HashMap<>();


    public Restaurant(String JSON, String ID) {
        try {

            JSONObject jsonObj = new JSONObject(JSON);
            // Extrayendo el arreglo de restaurantes
            JSONArray Restaurants = jsonObj.getJSONArray("restaurants");

            // Barriendo todos los restaurantes
            for (int i = 0; i < Restaurants.length(); i++) {
                JSONObject c = Restaurants.getJSONObject(i);

                if(c.getString("id").equals(ID)){
                    //Si este restaurant tiene el ID que buscamos, sacar sus datos
                    this.id = c.getString("id");
                    this.commercial_name = c.getString("commercial_name");
                    this.slogan =c.getString("slogan");
                    this.full_address=c.getString("full_address");
                    this.working_hours=c.getString("working_hours");
                    this.first_price="S/. "+c.getString("first_price");

                    //aca está la fumada de josé
                    this.menutitle =c.getJSONObject("first_menu").getString("type_name");
                    JSONObject firt_menu = c.getJSONObject("first_menu");
                    JSONArray submenujson = firt_menu.getJSONArray("submenus");
                    //**************** lol


                    //Lo limitamos a 5 opciones;
                    //Aqui se cambiaria a futuro*****************************
                    if(submenujson.length()>5){
                        this.submenulist = new String[5];
                    }
                    else{
                        this.submenulist = new String[submenujson.length()];
                    }
                    //*******************************************************


                    for (int j = 0; j < this.submenulist.length; j++){
                        JSONObject d = submenujson.getJSONObject(j);
                        HashMap<String,String> opcionestemp = new HashMap<>();

                        JSONArray opciones =d.getJSONArray("available_dishes");
                        String[] opcionesNamestemp = new String[opciones.length()];

                        for (int k = 0; k < opciones.length(); k++){
                            JSONObject e =opciones.getJSONObject(k);
                            opcionestemp.put(e.getString("dish_name"),e.getString("id"));
                            opcionesNamestemp[k]=e.getString("dish_name");
                        }

                        this.submenuNames.put(d.getString("submenu_type_name"),opcionesNamestemp);
                        this.submenuIDs.put(d.getString("submenu_type_name"),opcionestemp);
                        this.submenulist[j]= d.getString("submenu_type_name");
                    }
                }
            }

        } catch (final JSONException e) {
            Log.e("xxx JSON Handler", "Json parsing error: " + e.getMessage());
        }

    }

    public String getId() {
        return id;
    }

    public String getCommercial_name() {
        return commercial_name;
    }

    public String getSlogan() {
        return slogan;
    }

    public String getFull_address() {
        return full_address;
    }

    public String getWorking_hours() {
        return working_hours;
    }

    public String getFirst_price() {
        return first_price;
    }

    public String getMenutitle() {
        return menutitle;
    }

    public String[] getSubmenulist() {
        return submenulist;
    }

    public HashMap<String, HashMap<String, String>> submenuIDs() {
        return submenuIDs;
    }

    public HashMap<String, String[]> getSubmenuNames() {
        return submenuNames;
    }

    public HashMap<String, HashMap<String, String>> getSubmenuIDs() {
        return submenuIDs;
    }

    public String getSubmenuOpcionID(String Submenu_name, String Option){
        return submenuIDs.get(Submenu_name).get(Option);
    }

}
