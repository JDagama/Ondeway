package com.example.android.locationmanagerapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.location.Location;
import android.widget.Toast;
import android.support.v7.app.ActionBar;


public class MenuSearchActivity extends AppCompatActivity {
    EditText misCoordenadas;
    Location location;
    private String TAG = MainActivity.class.getSimpleName();
    private static String url = "http://test_324mfns.ondeway.com/restaurants/search";
    private ProgressDialog pDialog;
    String RespuestaServerJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_located);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.logondeway8);

        location = getIntent().getParcelableExtra("USER_LOCATION");

        misCoordenadas = (EditText) findViewById(R.id.editText);
        misCoordenadas.setText("Arroz con pollo");

        //Respuesta al Boton de buscar
        Button button = (Button) findViewById(R.id.button_search);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Buscar el menu en el servidor JSON
                new GetMenu().execute();
            }
        });
    }

    private class GetMenu extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MenuSearchActivity.this);
            pDialog.setMessage("Por favor espere...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Haciendo la consulta al servidor JSON Ondeway
            RespuestaServerJSON = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + RespuestaServerJSON);

            if (RespuestaServerJSON == null) {
                Log.e(TAG, "No se pudo obtener el JSON del servidor.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "No se pudo obtener el json del servidor. revisa el LogCat a ver que hay pes!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            Intent intent = new Intent(MenuSearchActivity.this, RestaurantListActivity.class);
            //EditText editText = (EditText) findViewById(R.id.editText);
            //String message = editText.getText().toString();

            intent.putExtra("JSON_RESPUESTA", RespuestaServerJSON);
            startActivity(intent);
        }

    }

}
