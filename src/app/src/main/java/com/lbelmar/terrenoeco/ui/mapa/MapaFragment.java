package com.lbelmar.terrenoeco.ui.mapa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.lbelmar.terrenoeco.R;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lbelmar.terrenoeco.databinding.FragmentMapaBinding;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;

public class MapaFragment extends Fragment {

    private MapaViewModel mapaViewModel;
    private FragmentMapaBinding binding;
    WebView webView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mapaViewModel =
                new ViewModelProvider(this).get(MapaViewModel.class);

        binding = FragmentMapaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        webView = (WebView) root.findViewById(R.id.web);
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                //view.loadUrl(url);
                System.out.println("hello");
                return false;
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        WebView.setWebContentsDebuggingEnabled(true);
        webView.getSettings().setAppCacheEnabled(false);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.loadUrl("http://192.168.1.134/mapa-movil.php");

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onResume() {
        super.onResume();
        webView.loadUrl("http://192.168.1.134/mapa-movil.php");

    }


}
