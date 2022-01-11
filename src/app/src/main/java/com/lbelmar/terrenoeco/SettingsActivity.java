// -------------------------------------------------------
// Autor: Adrian Maldonado
// Descripcion: Actividad de los ajustes
// Fecha: 18/12/2021
// -------------------------------------------------------

package com.lbelmar.terrenoeco;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    //Items de la vista
    Spinner spinnerInicio;
    Spinner spinnerFinal;

    //Preferencias
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Preferencias
        sharedPref = MainActivity.getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        //Items de la vista
        spinnerInicio = findViewById(R.id.spinner2);
        spinnerFinal = findViewById(R.id.spinner3);

        //Actualiza los valores con los guradados en las preferencias
        spinnerInicio.setSelection(sharedPref.getInt(MainActivity.getActivity().getString(R.string.nombre_clave_hora_inicio), 0) - 1);
        spinnerFinal.setSelection(sharedPref.getInt(MainActivity.getActivity().getString(R.string.nombre_clave_hora_final), 0) - 1);

        /**
         * Añade un listener cuando se cambie la hora en el selector
         */
        spinnerInicio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //Actualiza las preferencias con el valor seleccionado
                editor.putInt(MainActivity.getActivity().getString(R.string.nombre_clave_hora_inicio), position + 1);
                editor.apply();
                ServicioEscucharBeacons.mediaDiaria = new MediaDiaria();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /**
         * Añade un listener cuando se cambie la hora en el selector
         */
        spinnerFinal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //Actualiza las preferencias con el valor seleccionado
                editor.putInt(MainActivity.getActivity().getString(R.string.nombre_clave_hora_final), position + 1);
                editor.apply();
                ServicioEscucharBeacons.mediaDiaria = new MediaDiaria();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /**
     * Cierra la vista Settings
     *
     * @param view
     */
    public void cerrar(View view) {
        this.finish();
    }

    /**
     * Elimina el beacon de las preferencias
     *
     * @param v
     */
    public void eliminarDispositivoSeleccionado(View v) {
        editor.putString(MainActivity.getActivity().getString(R.string.nombre_clave_nombre_sensor), null);
        editor.apply();
    }
}
