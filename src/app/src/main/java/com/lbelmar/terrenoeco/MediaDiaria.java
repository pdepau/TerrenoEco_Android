package com.lbelmar.terrenoeco;
// -------------------------------------------------------
// Autor: Adrian Maldonado
// Descripcion: Gestiona las notificaciones de la app
// Fecha: 18/10/2021
// -------------------------------------------------------

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Debug;
import android.util.Log;

import com.lbelmar.terrenoeco.ui.sensor.SensorFragment;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class MediaDiaria {


    /**
     * Horas en las que trabajará la media
     */
    private int horaInicioJornada;
    private int horaFinalJornada;

    private Float media;
    private int minimo;
    private int maximo;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    private int cuantas;

    private int dia;
    private int mes;


    public MediaDiaria() {
        sharedPref = MainActivity.getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        horaInicioJornada = sharedPref.getInt(MainActivity.getActivity().getString(R.string.nombre_clave_hora_inicio), 0);
        horaFinalJornada = sharedPref.getInt(MainActivity.getActivity().getString(R.string.nombre_clave_hora_final), 24);

        media = sharedPref.getFloat(MainActivity.getActivity().getString(R.string.nombre_clave_media), 0);
        minimo = sharedPref.getInt(MainActivity.getActivity().getString(R.string.nombre_clave_minimo_diario), 64000);
        maximo = sharedPref.getInt(MainActivity.getActivity().getString(R.string.nombre_clave_maximo_diario), 0);

        cuantas = sharedPref.getInt(MainActivity.getActivity().getString(R.string.nombre_clave_cantidad_media), 1);

        dia = sharedPref.getInt(MainActivity.getActivity().getString(R.string.nombre_clave_dia_de_la_media), 1);
        mes = new Date().getMonth()+1;


    }

    public int getMinimo() {
        return minimo;
    }

    public void setMinimo(int minimo) {
        this.minimo = minimo;
        editor.putInt(MainActivity.getActivity().getString(R.string.nombre_clave_minimo_diario), minimo);

        editor.apply();
    }

    public int getMaximo() {
        return maximo;
    }

    public void setMaximo(int maximo) {
        this.maximo = maximo;
        editor.putInt(MainActivity.getActivity().getString(R.string.nombre_clave_maximo_diario), maximo);
        editor.apply();
    }

    /**
     * Devuelve la nedia
     *
     * @return
     */
    public float getMedia() {
        return this.media;
    }

    /**
     * Fuerza la media a ser ese valor
     *
     * @param media
     */
    private void setMedia(float media) {
        this.media = media;
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
            if (media.isNaN()){
                this.setMedia(valor);
            }
            media += (valor - media) / cuantas;

            this.setMedia(media);
            this.picosDiarios(valor);

            cuantas++;
            editor.putInt(MainActivity.getActivity().getString(R.string.nombre_clave_cantidad_media), cuantas);
            editor.apply();
        }


    }

    /**
     * Comprueba si la medida está en el rango de horas
     *
     * @return
     */
    public boolean comprobarHora() {


        Date date = new Date();
        int hora = date.getHours();
        int diaMedida = date.getDate();
        int mesMedida = date.getMonth();

        if (dia != diaMedida) {



            Set<String> historico = sharedPref.getStringSet(MainActivity.getActivity().getString(R.string.nombre_clave_historico),new HashSet<String>());

            String historicoHoy = "{Maximo:"+maximo+",Minimo:"+minimo+",Media:"+media+",Dia:"+dia+",Mes:"+mes+"}";

            historico.add(historicoHoy);

            Log.d("asd",historico.toString());

            editor.putStringSet(MainActivity.getActivity().getString(R.string.nombre_clave_historico),historico);

            editor.putInt(MainActivity.getActivity().getString(R.string.nombre_clave_cantidad_media), 1);
            editor.putInt(MainActivity.getActivity().getString(R.string.nombre_clave_dia_de_la_media), diaMedida);
            editor.putFloat(MainActivity.getActivity().getString(R.string.nombre_clave_media), 1);
            editor.putInt(MainActivity.getActivity().getString(R.string.nombre_clave_maximo_diario), 0);
            editor.putInt(MainActivity.getActivity().getString(R.string.nombre_clave_minimo_diario), 64000);

            maximo = 0;
            minimo = 64000;
            media = 1F;
            dia=diaMedida;
            mes=mesMedida;
            cuantas=1;

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

    private void picosDiarios(int valor) {

        if (valor > maximo) {
            setMaximo(valor);
            SensorFragment.actualizarTextoMaximo(valor + "");
        } else if (valor < minimo) {
            setMinimo(valor);
            SensorFragment.actualizarTextoMinimo(valor + "");
        } else {
            return;
        }
    }

}
