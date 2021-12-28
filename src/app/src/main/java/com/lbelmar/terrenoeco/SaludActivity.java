package com.lbelmar.terrenoeco;

import static com.lbelmar.terrenoeco.ui.mapa.MapaFragment.webView;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SaludActivity extends AppCompatActivity {
    private Button  botonCO;
    private Button botonNO2;
    private Button botonSO2;

    private ImageView barraCO;
    private ImageView barraNO2;
    private ImageView barraSO2;

    private ScrollView co;
    private ScrollView no2;
    private ScrollView so2;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salud);
        co= findViewById(R.id.scrollViewCO);
        no2=findViewById(R.id.scrollViewNO2);
        so2=findViewById(R.id.scrollViewSO2);
        barraCO=findViewById(R.id.imageViewBarraCO);
        barraNO2=findViewById(R.id.imageViewBarraNO2);
        barraSO2=findViewById(R.id.imageViewBarraSO2);
    }
    public void cerrar(View view){
        this.finish();
    }
    public void verCO(View view){
        co.setVisibility(View.VISIBLE);
        no2.setVisibility(View.INVISIBLE);
        so2.setVisibility(View.INVISIBLE);

        barraCO.setVisibility(View.VISIBLE);
        barraNO2.setVisibility(View.INVISIBLE);
        barraSO2.setVisibility(View.INVISIBLE);

    }
    public void verNO2(View view){
        co.setVisibility(View.INVISIBLE);
        no2.setVisibility(View.VISIBLE);
        so2.setVisibility(View.INVISIBLE);

        barraCO.setVisibility(View.INVISIBLE);
        barraNO2.setVisibility(View.VISIBLE);
        barraSO2.setVisibility(View.INVISIBLE);
    }
    public void verSO2(View view){
        co.setVisibility(View.INVISIBLE);
        no2.setVisibility(View.INVISIBLE);
        so2.setVisibility(View.VISIBLE);

        barraCO.setVisibility(View.INVISIBLE);
        barraNO2.setVisibility(View.INVISIBLE);
        barraSO2.setVisibility(View.VISIBLE);
    }
}
