package com.example.admallla.terrenoeco;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

/**
 * BeaconsActivity.java
 * <p>
 * AUTOR: Adrian Maldonado Llambies
 * FECHA: 17/10/2021
 * DESCRIPCION: Activity que maneja la lectura de los datos del beacon
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class BeaconsActivity extends AppCompatActivity implements LocationListener {

    private final String ETIQUETA_LOG = "BeaconsActivity";
    private Intent elIntentDelServicio = null;

    Context mContext;
    Activity mActivity;

    EditText e;
    TextView t;

    ServicioEscuharBeacons servicio;

    private static String latitud;
    private static String longitud;

    private static BeaconsActivity activity;

    protected LocationManager lm;
    public  Location l;
    boolean activado = false;

    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_beacons);
        mContext = this.getBaseContext();
        mActivity = this;


        e = findViewById(R.id.dispositivoBuscado);
        t = findViewById(R.id.textView2);

        activity = this;
        servicio = new ServicioEscuharBeacons();



        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);



        l = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        latitud = l.getLatitude()+"";
        longitud = l.getLongitude()+"";


    }

    /**
     *
     * Enciende el servicio
     *
     * @param v Vista requerida por el onClick
     */
    public void encenderServicioBuscarBeacons(View v) {
        Log.d(ETIQUETA_LOG, " boton arrancar servicio Pulsado");

        if (this.elIntentDelServicio != null) {
            // ya estaba arrancado
            Log.d(ETIQUETA_LOG, "ya estaba arrancado");

            return;
        }

        Log.d(ETIQUETA_LOG, " MainActivity.constructor : voy a arrancar el servicio");

        this.elIntentDelServicio = new Intent(this, ServicioEscuharBeacons.class);
        this.elIntentDelServicio.putExtra("tiempoDeEspera", (long) 5000);
        this.elIntentDelServicio.putExtra("dispositivoBuscado", e.getText().toString());

        startService(elIntentDelServicio);
        activado = true;


    }

    /**
     *
     * Apaga el servicio
     *
     * @param v Vista requerida por el onClick
     */
    public void apagar(View v) {
        if (this.elIntentDelServicio == null) {
            // no estaba arrancado
            Log.d(ETIQUETA_LOG, "no estaba arrancado");
            return;
        }

        stopService(this.elIntentDelServicio);

        this.elIntentDelServicio = null;

        Log.d(ETIQUETA_LOG, " boton detener servicio Pulsado");

    }


    // --------------------------------------------------------------
    // --------------------------------------------------------------
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void botonBuscarDispositivosBTLEPulsado(View v) {
        Log.d(ETIQUETA_LOG, " boton buscar dispositivos BTLE Pulsado");
        servicio.buscarTodosLosDispositivosBTLE();
    } // ()


    // --------------------------------------------------------------
    // --------------------------------------------------------------
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void botonBuscarNuestroDispositivoBTLEPulsado(View v) {
        Log.d(ETIQUETA_LOG, " boton nuestro dispositivo BTLE Pulsado");
        //this.buscarEsteDispositivoBTLE( Utilidades.stringToUUID( "EPSG-GTI-PROY-3A" ) );
        //this.buscarEsteDispositivoBTLE( "EPSG-GTI-PROY-3A" );
        servicio.buscarEsteDispositivoBTLE(e.getText().toString());

    } // ()


    // --------------------------------------------------------------
    // --------------------------------------------------------------
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void botonDetenerBusquedaDispositivosBTLEPulsado(View v) {
        Log.d(ETIQUETA_LOG, " boton detener busqueda dispositivos BTLE Pulsado");
        servicio.detenerBusquedaDispositivosBTLE();
    } // ()





    public static String getLatitud() {
        return latitud;
    }

    public static String getLongitut() {
        return longitud;
    }

    public static BeaconsActivity getInstance() {
        return activity;
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
