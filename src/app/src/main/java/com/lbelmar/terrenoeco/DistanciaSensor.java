package com.lbelmar.terrenoeco;
// -------------------------------------------------------
// Autor: Adrian Maldonado
// Descripcion: AGestiona las notificaciones de la app
// Fecha: 19/10/2021
// -------------------------------------------------------
import android.content.Context;
import android.content.res.Resources;

public class DistanciaSensor {

    float umbralMuyAlto =  -80;
    float umbralAlto =  -75;
    float umbralCerca =  -40;


    private Resources resources;

    /**
     * Constructor
     */
    public DistanciaSensor(){
        resources = MainActivity.getContext().getResources();
    }


    /**
     *
     *Devuelve un texto con la distancia relativa entre el sensor y el dispositivo Android
     *
     * @param valor
     * @return
     */
    public String distanciaRelativa(float valor){

        if(valor < umbralMuyAlto){
            return resources.getString(R.string.textoSensorMuyLejos);
        }else if(valor < umbralAlto){
            return resources.getString(R.string.textoSensorLejos);
        }else if(valor < umbralCerca){
            return resources.getString(R.string.textoSensorCerca);
        }else {
            return resources.getString(R.string.textoSensorMuyCerca);
        }

    }

}
