package com.lbelmar.terrenoeco.ui.sensor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.lbelmar.terrenoeco.MainActivity;
import com.lbelmar.terrenoeco.R;
import com.lbelmar.terrenoeco.SettingsActivity;
import com.lbelmar.terrenoeco.databinding.FragmentSensorBinding;

public class SensorFragment extends Fragment {

    private SensorViewModel sensorViewModel;
    private FragmentSensorBinding binding;

    public static TextView textoMedida;
    public static TextView textoMinimo;
    public static TextView textoMaximo;
    public static TextView textoDistancia;
    public static ImageView iconoDistancia;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    static ImageView fondoColorMedia;
    static ImageView fondoColorMaximo;
    static ImageView fondoColorMinimo;


    static Integer umbralAlto = 40;
    static Integer umbralMedio = 20;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sensorViewModel = new ViewModelProvider(this).get(SensorViewModel.class);
        //new ViewModelProvider(this).get(SensorViewModel.class);

        binding = FragmentSensorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iconoDistancia = view.findViewById(R.id.iconoDistancia);
        textoMedida = view.findViewById(R.id.textoMedia);
        textoMinimo = view.findViewById(R.id.textoMinimo);
        textoMaximo = view.findViewById(R.id.textoMaximo);
        textoDistancia = view.findViewById(R.id.textoDistancia);
        fondoColorMedia = view.findViewById(R.id.indicadorColorMedia);
        fondoColorMaximo = view.findViewById(R.id.indicadorColorMaximo);
        fondoColorMinimo = view.findViewById(R.id.indicadorColorMinimo);

        sharedPref = MainActivity.getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        int max = sharedPref.getInt(MainActivity.getActivity().getString(R.string.nombre_clave_maximo_diario), 0);
        int min = sharedPref.getInt(MainActivity.getActivity().getString(R.string.nombre_clave_minimo_diario), 64000);
        float mid = sharedPref.getFloat(MainActivity.getActivity().getString(R.string.nombre_clave_media), 0);

        Log.d("adsa",max + " : " + min);
        actualizarTextoMinimo(min+"");
        actualizarTextoMaximo(max+"");
        actualizarTextoMedida(mid+"");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static void actualizarTextoMedida(String texto) {
        //en caso de que no esté en esta vista
        if (textoMedida != null) {
            textoMedida.setText(texto);
            float media = Float.parseFloat(texto);
            if (media > umbralAlto){
                fondoColorMedia.setColorFilter(ContextCompat.getColor(MainActivity.getContext(),R.color.Rogo));
            }else if(media > umbralMedio){
                fondoColorMedia.setColorFilter(ContextCompat.getColor(MainActivity.getContext(),R.color.Narhanga));
            }else {
                fondoColorMedia.setColorFilter(ContextCompat.getColor(MainActivity.getContext(),R.color.Berde));
            }
        }
    }

    public static void actualizarTextoMinimo(String texto) {
        //en caso de que no esté en esta vista
        if (textoMinimo != null) {
            textoMinimo.setText(texto);

            float media = Float.parseFloat(texto);
            if (media > umbralAlto){
                fondoColorMinimo.setColorFilter(ContextCompat.getColor(MainActivity.getContext(),R.color.Rogo));
            }else if(media > umbralMedio){
                fondoColorMinimo.setColorFilter(ContextCompat.getColor(MainActivity.getContext(),R.color.Narhanga));
            }else {
                fondoColorMinimo.setColorFilter(ContextCompat.getColor(MainActivity.getContext(),R.color.Berde));
            }
        }
    }

    public static void actualizarTextoMaximo(String texto) {
        //en caso de que no esté en esta vista
        if (textoMaximo != null) {
            textoMaximo.setText(texto);

            float media = Float.parseFloat(texto);
            if (media > umbralAlto){
                fondoColorMaximo.setColorFilter(ContextCompat.getColor(MainActivity.getContext(),R.color.Rogo));
            }else if(media > umbralMedio){
                fondoColorMaximo.setColorFilter(ContextCompat.getColor(MainActivity.getContext(),R.color.Narhanga));
            }else {
                fondoColorMaximo.setColorFilter(ContextCompat.getColor(MainActivity.getContext(),R.color.Berde));
            }
        }
    }

    public static void actualizarTextoDistancia(String texto) {
        //en caso de que no esté en esta vista
        if (textoDistancia != null) {
            actualizarIconoDistancia(texto);
            textoDistancia.setText(texto);
        }
    }

    public static void actualizarIconoDistancia(String texto) {


        String textoMuyLejos = MainActivity.getContext().getResources().getString(R.string.textoSensorMuyLejos);
        String textoLejos = MainActivity.getContext().getResources().getString(R.string.textoSensorLejos);
        String textoCerca = MainActivity.getContext().getResources().getString(R.string.textoSensorCerca);
        String textoMuyCerca = MainActivity.getContext().getResources().getString(R.string.textoSensorMuyLejos);


        if (texto.equals(textoMuyLejos)) {
            iconoDistancia.setImageResource(R.drawable.ic_mala);
        } else if (texto.equals(textoLejos)) {
            iconoDistancia.setImageResource(R.drawable.ic_regular);
        } else if (texto.equals(textoCerca)) {
            iconoDistancia.setImageResource(R.drawable.ic_good2);
        } else {
            iconoDistancia.setImageResource(R.drawable.ic_good);
        }

    }
    public void abrirSettings(View v){
        Intent intent = new Intent(getContext(), SettingsActivity.class);
        startActivity(intent);
    }

    public static void aaa(int a, String b){
        Log.d("ADRA",b+"aa");


        if (a == 200){
            String[] strings = b.split(":");

            umbralMedio = Integer.parseInt((strings[4].split(","))[0]);
            umbralAlto = Integer.parseInt((strings[3].split(","))[0]);
        }




    }
}
