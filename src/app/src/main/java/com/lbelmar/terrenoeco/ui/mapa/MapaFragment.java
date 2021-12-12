package com.lbelmar.terrenoeco.ui.mapa;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.lbelmar.terrenoeco.R;
import com.lbelmar.terrenoeco.databinding.FragmentMapaBinding;

public class MapaFragment extends Fragment {

    private MapaViewModel mapaViewModel;
    private FragmentMapaBinding binding;
    WebView webView;

    TextView imageView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mapaViewModel =
                new ViewModelProvider(this).get(MapaViewModel.class);

        binding = FragmentMapaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        imageView = root.findViewById(R.id.textView11);
        webView = (WebView) root.findViewById(R.id.web);
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

                imageView.setVisibility(View.VISIBLE);

            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        WebView.setWebContentsDebuggingEnabled(true);
        webView.getSettings().setAppCacheEnabled(false);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.loadUrl("http://192.168.1.134/Terreno-Eco/mapa-movil.php");

        //Log.d("aaaaaa", webView.getStateDescription()+"");

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
        webView.loadUrl("http://192.168.1.134/Terreno-Eco/mapa-movil.php");

    }


}
