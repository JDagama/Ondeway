package com.example.android.locationmanagerapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.location.Location;
import android.view.View;


public class LocatedActivity extends AppCompatActivity {
    EditText misCoordenadas;
    Location location;
    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_located);
        location = getIntent().getParcelableExtra("USER_LOCATION");

        misCoordenadas = (EditText) findViewById(R.id.editText);
        misCoordenadas.setText(String.valueOf(location.getLongitude())+" y "+String.valueOf(location.getLatitude()));

        //Respuesta al Boton de buscar
        Button button = (Button) findViewById(R.id.button_search);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i(TAG, "xxx Tus Nuevas coordenadas "+String.valueOf(location.getLongitude())+" y "+String.valueOf(location.getLatitude()) );
                misCoordenadas.setText(String.valueOf(location.getLongitude())+" y "+String.valueOf(location.getLatitude()));
            }
        });
    }

}
