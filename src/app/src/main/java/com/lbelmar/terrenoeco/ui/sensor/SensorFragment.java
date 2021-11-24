package com.lbelmar.terrenoeco.ui.sensor;

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

import com.lbelmar.terrenoeco.databinding.FragmentSensorBinding;

public class SensorFragment extends Fragment {

    private SensorViewModel sensorViewModel;
    private FragmentSensorBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sensorViewModel =
                new ViewModelProvider(this).get(SensorViewModel.class);
                //new ViewModelProvider(this).get(SensorViewModel.class);

        binding = FragmentSensorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textoDistancia2;
        sensorViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
