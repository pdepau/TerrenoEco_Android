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

    Spinner spinnerInicio;
    Spinner spinnerFinal;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPref = MainActivity.getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        spinnerInicio = findViewById(R.id.spinner2);
        spinnerFinal = findViewById(R.id.spinner3);

        spinnerInicio.setSelection(sharedPref.getInt(MainActivity.getActivity().getString(R.string.nombre_clave_hora_inicio), 0)-1);
        spinnerFinal.setSelection(sharedPref.getInt(MainActivity.getActivity().getString(R.string.nombre_clave_hora_final), 0)-1);

        spinnerInicio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor.putInt(MainActivity.getActivity().getString(R.string.nombre_clave_hora_inicio), position+1);
                editor.apply();
                ServicioEscucharBeacons.mediaDiaria = new MediaDiaria();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerFinal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor.putInt(MainActivity.getActivity().getString(R.string.nombre_clave_hora_final), position+1);
                editor.apply();
                ServicioEscucharBeacons.mediaDiaria = new MediaDiaria();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void cerrar(View view){
        this.finish();
    }
    public void eliminarDispositivoSeleccionado(View v) {
        editor.putString(MainActivity.getActivity().getString(R.string.nombre_clave_nombre_sensor),null);
        editor.apply();
        Log.d("asdfgh","1");
    }
}
