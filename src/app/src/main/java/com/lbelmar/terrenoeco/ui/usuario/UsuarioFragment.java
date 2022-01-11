package com.lbelmar.terrenoeco.ui.usuario;

// -------------------------------------------------------
// Autor: Pau Blanes
// Descripcion: UsuarioFragment
// Fecha: 28/12/2021
// -------------------------------------------------------

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.lbelmar.terrenoeco.SaludActivity;
import com.lbelmar.terrenoeco.SettingsActivity;
import com.lbelmar.terrenoeco.databinding.FragmentUsuarioBinding;

public class UsuarioFragment extends Fragment {

    private UsuarioViewModel usuarioViewModel;
    private FragmentUsuarioBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        usuarioViewModel =
                new ViewModelProvider(this).get(UsuarioViewModel.class);

        binding = FragmentUsuarioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void abrirSalud(View v) {
        Intent intent = new Intent(getContext(), SaludActivity.class);
        startActivity(intent);
    }
}
