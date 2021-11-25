package com.lbelmar.terrenoeco;
// -------------------------------------------------------
// Autor: Adrian Maldonado
// Descripcion: Gestiona las notificaciones de la app
// Fecha: 18/10/2021
// -------------------------------------------------------
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Date;

public class MediaDiaria {


    /**
     * Horas en las que trabajará la media
     */
    private int horaInicioJornada;
    private int horaFinalJornada;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    public MediaDiaria() {
        sharedPref = MainActivity.getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        horaInicioJornada = 8;
        horaFinalJornada = 22;

    }

    /**
     * Devuelve la nedia
     *
     * @return
     */
    public float getMedia() {

        return sharedPref.getFloat(MainActivity.getActivity().getString(R.string.nombre_clave_media), 0);

    }

    /**
     * Fuerza la media a ser ese valor
     *
     * @param media
     */
    private void setMedia(float media) {
        editor.putFloat(MainActivity.getActivity().getString(R.string.nombre_clave_media), media);
        editor.apply();

    }


    /**
     * Actualiza la media diaria añadiendo el valor
     *
     * @param valor
     */
    public void actualizarMedia(int valor) {

        if (comprobarHora()) {
            int cuantas = sharedPref.getInt(MainActivity.getActivity().getString(R.string.nombre_clave_cantidad_media), 1);
            float media = this.getMedia();

            media += (valor - media) / cuantas;

            this.setMedia(media);

            cuantas++;
            Log.d("aa","Cuantas : " + cuantas);
            editor.putInt(MainActivity.getActivity().getString(R.string.nombre_clave_cantidad_media), cuantas);
            editor.apply();
        }


    }

    /**
     *
     * Comprueba si la medida está en el rango de horas
     *
     * @return
     */
    public boolean comprobarHora() {

        int diaDeLaMedia = sharedPref.getInt(MainActivity.getActivity().getString(R.string.nombre_clave_dia_de_la_media), 1);


        Date date = new Date();
        int hora = date.getHours();
        int diaMedida = date.getDate();

        Log.d("aa",diaMedida + " act :  saved " + diaDeLaMedia);

        if(diaDeLaMedia!=diaMedida){
            editor.putInt(MainActivity.getActivity().getString(R.string.nombre_clave_cantidad_media), 1);
            editor.putInt(MainActivity.getActivity().getString(R.string.nombre_clave_dia_de_la_media), diaMedida);
            editor.putFloat(MainActivity.getActivity().getString(R.string.nombre_clave_media), 1);
            editor.apply();
        }
        if (hora < horaInicioJornada) {
            return false;
        } else if (hora >= horaFinalJornada) {
            return false;
        } else {
            return true;
        }

    }

}
