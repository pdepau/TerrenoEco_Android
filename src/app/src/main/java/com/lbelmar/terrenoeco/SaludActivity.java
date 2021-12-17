package com.lbelmar.terrenoeco;

import static com.lbelmar.terrenoeco.ui.mapa.MapaFragment.webView;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SaludActivity extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salud);
        webView = (WebView) this.findViewById(R.id.webSalud);
        webView.loadUrl("http://192.168.0.164/TerrenoEco_Frontend/src/salud-movil.php");
    }
    public void cerrar(View view){
        this.finish();
    }
}
