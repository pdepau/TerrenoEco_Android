package com.lbelmar.terrenoeco.ui.sensor;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.lbelmar.terrenoeco.MainActivity;
import com.lbelmar.terrenoeco.R;
import com.lbelmar.terrenoeco.databinding.FragmentSensorBinding;

public class SensorFragment extends Fragment {

    private SensorViewModel sensorViewModel;
    private FragmentSensorBinding binding;

    public static TextView textoMedida;
    public static TextView textoDistancia;
    public static ImageView iconoDistancia;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sensorViewModel =
                new ViewModelProvider(this).get(SensorViewModel.class);
                //new ViewModelProvider(this).get(SensorViewModel.class);

        binding = FragmentSensorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        iconoDistancia=root.findViewById(R.id.iconoDistancia);
        textoMedida=root.findViewById(R.id.textoVisualMedida2);
        textoDistancia=root.findViewById(R.id.textoDistancia);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static void actualizarTextoMedida(String texto) {
        //en caso de que no esté en esta vista
        if(textoMedida!=null){
            textoMedida.setText(texto);
        }
    }

    public static void actualizarTextoDistancia(String texto) {
        //en caso de que no esté en esta vista
        if(textoDistancia!=null){
            actualizarIconoDistancia(texto);
            textoDistancia.setText(texto);
        }
    }

    public static void actualizarIconoDistancia(String texto){


        String textoMuyLejos = MainActivity.getContext().getResources().getString(R.string.textoSensorMuyLejos);
        String textoLejos = MainActivity.getContext().getResources().getString(R.string.textoSensorLejos);
        String textoCerca = MainActivity.getContext().getResources().getString(R.string.textoSensorCerca);
        String textoMuyCerca = MainActivity.getContext().getResources().getString(R.string.textoSensorMuyLejos);


        if(texto.equals(textoMuyLejos)){
            iconoDistancia.setImageResource(R.drawable.ic_mala);
        }else if(texto.equals(textoLejos)){
            iconoDistancia.setImageResource(R.drawable.ic_regular);
        }else if(texto.equals(textoCerca)){
            iconoDistancia.setImageResource(R.drawable.ic_good2);
        }else {
            iconoDistancia.setImageResource(R.drawable.ic_good);
        }

    }
}
