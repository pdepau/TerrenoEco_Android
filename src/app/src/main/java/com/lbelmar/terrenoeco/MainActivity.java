package com.lbelmar.terrenoeco;

// -------------------------------------------------------
// Autor: Luis Belloch
// Autor: Adrian Maldonado
// Descripcion: MainActivity
// Fecha: 15/10/2021
// -------------------------------------------------------

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lbelmar.terrenoeco.databinding.ActivityMainBinding;
import com.lbelmar.terrenoeco.ui.mapa.MapaFragment;


// ------------------------------------------------------------------
// ------------------------------------------------------------------

public class MainActivity extends AppCompatActivity {

    // --------------------------------------------------------------
    // --------------------------------------------------------------
    private static final String ETIQUETA_LOG = ">>>>";

    private static final int CODIGO_PETICION_PERMISOS = 11223344;

    private Intent elIntentDelServicio = null;

    static Context mContext;
    static Activity mActivity;

    private ActivityMainBinding binding;

    //Booleano para que no se actualize constantemente el mapa con cada medida
    static boolean esperarParaActualizarMapa = false;

    // --------------------------------------------------------------
    // --------------------------------------------------------------
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(ETIQUETA_LOG, " onCreate(): empieza ");
        mContext = this;
        mActivity = this;
        /**
         * Parte de las pestañas
         */
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        //Contexto y actividad para las clases abstractas


        /**
         * Peticion de los permisos
         */
        if (
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, CODIGO_PETICION_PERMISOS);
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, CODIGO_PETICION_PERMISOS);
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, CODIGO_PETICION_PERMISOS);
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODIGO_PETICION_PERMISOS);
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CODIGO_PETICION_PERMISOS);
        } else {
            Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): parece que YA tengo los permisos necesarios !!!!");

        }

        //Arrancar el servicio cuando se inicia la app

        arrancarServicio();
        Log.d(ETIQUETA_LOG, " onCreate(): termina ");
        Logica.obtenerTipo(2);


    } // onCreate()


    /**
     * Actualiza los permisos cuando se piden
     *
     * @param requestCode  codigo de permiso
     * @param permissions  lista de permisos
     * @param grantResults resultados de los permisos
     */
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case CODIGO_PETICION_PERMISOS:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(ETIQUETA_LOG, " onRequestPermissionResult(): permisos concedidos  !!!!");
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                } else {

                    Log.d(ETIQUETA_LOG, " onRequestPermissionResult(): Socorro: permisos NO concedidos  !!!!");

                }
                return;
        }
        // Other 'case' lines to check for other
        // permissions this app might request.
    } // ()

    /**
     * Arrancar el servicio de escucha de beacons
     */
    public void arrancarServicio() {
        Log.d(ETIQUETA_LOG, " boton arrancar servicio Pulsado");

        if (this.elIntentDelServicio != null) {
            // ya estaba arrancado
            return;
        }

        Log.d(ETIQUETA_LOG, " MainActivity.constructor : voy a arrancar el servicio");

        this.elIntentDelServicio = new Intent(this, ServicioEscucharBeacons.class);

        this.elIntentDelServicio.putExtra("tiempoDeEspera", (long) 5000);
        startService(this.elIntentDelServicio);

    } // ()

    /**
     * Funcion para abrir la vista settings
     *
     * @param v
     */
    public void abrirSettings(View v) {
        Intent intent = new Intent(getContext(), SettingsActivity.class);
        startActivity(intent);
    }

    public void abrirSalud(View v) {
        Intent intent = new Intent(getContext(), SaludActivity.class);
        startActivity(intent);
    }


    /**
     *
     * Centra el mapa del Webview en la ubicacion que le pasas
     *
     * @param lat
     * @param lon
     */
    public static void centrarMapaEnUbicacion(double lat, double lon) {

        //en caso de que el mapa no este iniciado
        if(MapaFragment.webView == null)return;

        if (!esperarParaActualizarMapa) {
            esperarParaActualizarMapa = true;
            MapaFragment.webView.loadUrl("javascript:centrarMapaEnUbicacion(" + lat + "," + lon + ")");
            ServicioEscucharBeacons.getLocation();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    esperarParaActualizarMapa = false;
                }
            }, 1000);
        }
    }

    /**
     * Devuelve el contexto
     *
     * @return
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * Devuelve la actividad
     *
     * @return
     */
    public static Activity getActivity() {
        return mActivity;
    }


} // class
// --------------------------------------------------------------
// --------------------------------------------------------------
// --------------------------------------------------------------
// --------------------------------------------------------------


