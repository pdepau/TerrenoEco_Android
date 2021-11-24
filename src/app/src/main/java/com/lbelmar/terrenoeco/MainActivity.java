package com.lbelmar.terrenoeco;

// -------------------------------------------------------
// Autor: Luis Belloch
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
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lbelmar.terrenoeco.databinding.ActivityMainBinding;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


// ------------------------------------------------------------------
// ------------------------------------------------------------------

public class MainActivity extends AppCompatActivity {

    // --------------------------------------------------------------
    // --------------------------------------------------------------
    private static final String ETIQUETA_LOG = ">>>>";

    private static final int CODIGO_PETICION_PERMISOS = 11223344;

    public static final String TAG_INTENT_SERVICE = "TAG_INTENT_SERVICE";

    public static TextView textoMedida;
    public static TextView textoDistancia;

    private Intent elIntentDelServicio = null;
    GestionNotificaciones gestorNotidicaciones;


    static Context mContext;
    static Activity mActivity;

    MediaDiaria mediaDiaria;



    private ActivityMainBinding binding;
    // --------------------------------------------------------------
    // --------------------------------------------------------------
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        textoMedida=findViewById(R.id.textoVisualMedida2);
        textoDistancia=findViewById(R.id.textoDistancia2);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


        mContext = this;
        mActivity = this;
        Log.d(ETIQUETA_LOG, " onCreate(): empieza ");

        if (
                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.ACCESS_FINE_LOCATION},
                    CODIGO_PETICION_PERMISOS);
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    CODIGO_PETICION_PERMISOS);
        } else {
            Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): parece que YA tengo los permisos necesarios !!!!");

        }


        gestorNotidicaciones = new GestionNotificaciones();

        mediaDiaria = new MediaDiaria();
        mediaDiaria.actualizarMedia(1);
        Log.d("aa", mediaDiaria.getMedia() + "");

        Log.d(ETIQUETA_LOG, " onCreate(): termina ");




    } // onCreate()

    // --------------------------------------------------------------
    // --------------------------------------------------------------

    /**
     * Actualiza los permisos cuando se piden
     *
     * @param requestCode  codigo de permiso
     * @param permissions  lista de permisos
     * @param grantResults resultados de los permisos
     */
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case CODIGO_PETICION_PERMISOS:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {

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

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    public void botonArrancarServicioPulsado(View v) {
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

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    public void botonDetenerServicioPulsado(View v) {

        if (this.elIntentDelServicio == null) {
            // no estaba arrancado
            return;
        }

        stopService(this.elIntentDelServicio);

        this.elIntentDelServicio = null;

        Log.d(ETIQUETA_LOG, " boton detener servicio Pulsado");


    } // ()


    public static Context getContext() {
        return mContext;
    }

    public static Activity getActivity() {
        return mActivity;
    }

    public static void actualizarTextoMedida(String texto) {
        textoMedida.setText(texto);
    }

    public static void actualizarTextoDistancia(String texto) {
        textoDistancia.setText(texto);
    }


} // class
// --------------------------------------------------------------
// --------------------------------------------------------------
// --------------------------------------------------------------
// --------------------------------------------------------------


