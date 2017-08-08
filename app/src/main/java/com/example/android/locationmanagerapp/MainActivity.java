package com.example.android.locationmanagerapp;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.location.LocationManager;
import android.location.LocationListener;
import android.location.Location;
import android.content.Context;
import android.view.View;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;
import android.content.pm.PackageManager;
import android.util.Log;
import android.support.design.widget.Snackbar;


public class MainActivity extends AppCompatActivity {
    private Location currentBestLocation = null;
    static final int TIMEOUT = 1000 * 60 * 2;
    final private int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;
    private String TAG = MainActivity.class.getSimpleName();
    private View mLayout;
    public LocationManager locationManager;
    public LocationListener locationListener;
    private boolean userLocated = false;
    Intent intent1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "xxx Comenzamos pesss");
        mLayout = findViewById(R.id.main_layout_radar);
        intent1 = new Intent(MainActivity.this, MenuSearchActivity.class);

        //Validando los permisos
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            Log.i(TAG, "xxx Se requiere algun permiso");
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION)) {

                Log.i(TAG, "xxx Ya habia rechazado");
                Snackbar.make(mLayout, "Esta aplicación requiere acceder a su localización", Snackbar.LENGTH_INDEFINITE)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Log.i(TAG, "xxx Funciono el snackbar");
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                            }
                        }).show();


            } else {

                // No explanation needed, we can request the permission.
                Log.i(TAG, "xxx Solicitando permisos");
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        } else {

            Log.i(TAG, "xxx ya se tenian todos los permisos");
            //Empiezo a buscar la ubicacion del cliente;
            starGettingLocation(locationManager);
        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.i(TAG, "xxx Hubo respuesta");
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "xxx La respuesta fue positiva");

                    //Empiezo a buscar la ubicacion del cliente;
                    starGettingLocation(locationManager);


                } else {
                    Log.i(TAG, "xxx La respuesta fue negativa");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    this.finish();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void starGettingLocation(final LocationManager locationManager) {

        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                Log.i(TAG, "xxx Llegó un update GPS");
                makeUseOfNewLocation(location);
                //cambio de pagina luego de la primera actualizacion
                intent1.putExtra("USER_LOCATION", currentBestLocation);
                locationManager.removeUpdates(locationListener);
                startActivity(intent1);
                MainActivity.this.finish();
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
        String locationProvider = LocationManager.GPS_PROVIDER;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Log.i(TAG, "xxx Solicitando permisos desde llamada");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            return;
        }
        Log.i(TAG, "xxx Empezando a recibir updates");
        locationManager.requestLocationUpdates(locationProvider, 10000, 0, locationListener);
    }








    //*****************************************TOOLS*********************************************
    //*******************************************************************************************
    private void makeUseOfNewLocation(Location location) {
        if ( isBetterLocation(location, currentBestLocation) ) {
            Log.i(TAG, "xxx Update GPS aceptado");
            currentBestLocation = location;
            Log.i(TAG, "xxx   Longitud" + String.valueOf(currentBestLocation.getLongitude()));
            Log.i(TAG, "xxx   Latitud" + String.valueOf(currentBestLocation.getLatitude()));
        }
    }

    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TIMEOUT;
        boolean isSignificantlyOlder = timeDelta < -TIMEOUT;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location,
        // because the user has likely moved.
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse.
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }





}
