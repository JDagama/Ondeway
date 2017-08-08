package com.example.android.locationmanagerapp;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class RestaurantDetailActivity extends AppCompatActivity {
    private String TAG = RestaurantListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        //Extrayendo datos de la vista anterior
        final String ID = getIntent().getStringExtra("RESTAURANT_ID");
        final String JSON = getIntent().getStringExtra("JSON_RESPUESTA");
        final Restaurant restaurant = new Restaurant(JSON,ID);


        //Llenando las vistas generales del restaurante
        //Datos del restaurante y Titulo del menu
        TextView commercial_name = (TextView)findViewById(R.id.commercial_name);
        commercial_name.setText(restaurant.getCommercial_name());
        TextView slogan = (TextView)findViewById(R.id.slogan);
        slogan.setText(restaurant.getSlogan());
        TextView full_address = (TextView)findViewById(R.id.full_address);
        full_address.setText(restaurant.getFull_address());
        TextView working_hours = (TextView)findViewById(R.id.working_hours);
        working_hours.setText(restaurant.getWorking_hours());
        TextView menu_title = (TextView)findViewById(R.id.menutitle);
        menu_title.setText(restaurant.getMenutitle());


        //******El acople perfecto*****
        final String[] Submenus = restaurant.getSubmenulist();
        final HashMap<String, String[]> Sme = restaurant.getSubmenuNames();
        //*****************************

        for (int i=0; i< Submenus.length; i++){
            String titleID = "title" + i;
            String radiogroupID = "rg" + i;
            int titleResID = getResources().getIdentifier(titleID, "id",getPackageName());
            int radiogroupResID = getResources().getIdentifier(radiogroupID,"id",getPackageName());

            TextView text =(TextView) findViewById(titleResID);
            text.setText(Submenus[i]);

            RadioGroup rg = (RadioGroup) findViewById(radiogroupResID);
            //suponiendo que nunca habran dos submenus con el mismo nombre
            String[] Opciones = Sme.get(Submenus[i]);

            for(int j =0; j<Opciones.length;j++)
            {
                RadioButton radioButtonView = new RadioButton(this);
                radioButtonView.setId(j);
                radioButtonView.setText(Opciones[j]);
                radioButtonView.setTextColor(Color.WHITE);

                rg.addView(radioButtonView, new ActionBar.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            rg.check(0);
        }

        Button button = (Button) findViewById(R.id.button_search);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(RestaurantDetailActivity.this, OrdenarActivity.class);

                String[] chosenNames = new String[Submenus.length];
                String[] chosenIDs = new String[Submenus.length];

                for (int i=0; i< Submenus.length; i++){

                    //Ubicando los radiogroups de forma programatica
                    String radiogroupID = "rg" + i;
                    int radiogroupResID = getResources().getIdentifier(radiogroupID,"id",getPackageName());
                    RadioGroup rg = (RadioGroup) findViewById(radiogroupResID);

                    //Sacando los nombres y IDs de las opciones elegidas
                    int chosen = rg.getCheckedRadioButtonId();
                    chosenNames[i] =Sme.get(Submenus[i])[chosen];
                    chosenIDs[i] =restaurant.getSubmenuOpcionID(Submenus[i],chosenNames[i]);

                    Log.i(TAG,"xxx nombres: "+chosenNames[i] );
                    Log.i(TAG,"xxx IDs: "+ chosenIDs[i]);
                }

                intent.putExtra("JSON_RESPUESTA",JSON);
                intent.putExtra("RESTAURANT_ID",ID);
                intent.putExtra("IDs",chosenIDs);
                intent.putExtra("Names",chosenNames);
                startActivity(intent);

            }
        });



    }
}
