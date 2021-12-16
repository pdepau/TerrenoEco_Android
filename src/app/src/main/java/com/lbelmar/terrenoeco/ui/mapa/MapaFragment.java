package com.lbelmar.terrenoeco.ui.mapa;

// -------------------------------------------------------
// Autor: Pau Blanes
// Autor: Adrian Maldonado
// Descripcion: MapaFragment
// Fecha: 28/11/2021
// -------------------------------------------------------

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.lbelmar.terrenoeco.MainActivity;
import com.lbelmar.terrenoeco.R;
import com.lbelmar.terrenoeco.ServicioEscucharBeacons;
import com.lbelmar.terrenoeco.databinding.FragmentMapaBinding;

public class MapaFragment extends Fragment {

    private MapaViewModel mapaViewModel;
    private FragmentMapaBinding binding;
    public static WebView webView;

    TextView imageView;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mapaViewModel =
                new ViewModelProvider(this).get(MapaViewModel.class);

        binding = FragmentMapaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPref = MainActivity.getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        imageView = view.findViewById(R.id.textView11);
        webView = (WebView) view.findViewById(R.id.web);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //view.loadUrl(url);
                System.out.println("hello");
                return false;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);

                //imageView.setVisibility(View.VISIBLE);

            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                if (consoleMessage.message().contains("DatosEcoparada")) {
                    Log.d("Web", consoleMessage.message());
                    String[] valores = consoleMessage.message().split(",");
                    int valorDelTipodelSensor = Integer.parseInt(valores[2]);
                    float rango = valorDelTipodelSensor * 0.2F;
                    int calibracion = valorDelTipodelSensor - ServicioEscucharBeacons.ultimaMedida;
                    if (rango < calibracion){
                        editor.putInt(MainActivity.getActivity().getString(R.string.nombre_clave_valor_calibracion), calibracion);
                        ServicioEscucharBeacons.valorCalibracion = calibracion;
                        editor.apply();
                    }

                }
                return true;
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        WebView.setWebContentsDebuggingEnabled(true);
        webView.getSettings().setAppCacheEnabled(false);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        webView.loadUrl("http://192.168.1.134/TerrenoEco_Frontend/src/mapa-movil.php");
        Log.d("asdas", webView.getProgress() + "a");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
