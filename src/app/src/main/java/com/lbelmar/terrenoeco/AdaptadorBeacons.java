package com.lbelmar.terrenoeco;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lbelmar.terrenoeco.ui.sensor.SensorFragment;

import java.util.ArrayList;

public class AdaptadorBeacons extends RecyclerView.Adapter<AdaptadorBeacons.ViewHolder> {

    private final ArrayList<String> localDataSet;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final ImageButton button;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.textView);
            button = (ImageButton) view.findViewById(R.id.imageButton);
        }

        public TextView getTextView() {
            return textView;
        }

        public ImageButton getButton() {
            return button;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     *                by RecyclerView.
     */
    public AdaptadorBeacons(ArrayList<String> dataSet) {
        localDataSet = dataSet;
        sharedPref = MainActivity.getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.beacon_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(localDataSet.get(position));
        viewHolder.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("a", localDataSet.get(position) + "");
                editor.putString(MainActivity.getActivity().getString(R.string.nombre_clave_nombre_sensor),localDataSet.get(position));
                editor.apply();
                SensorFragment.actualizarVisibilidadRecycler();
            }
        });
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}


