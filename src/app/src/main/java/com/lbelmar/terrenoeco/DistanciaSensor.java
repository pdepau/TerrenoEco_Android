package com.lbelmar.terrenoeco;

import android.content.Context;
import android.content.res.Resources;

public class DistanciaSensor {

    float umbralMuyAlto =  -80;
    float umbralAlto =  -75;
    float umbralCerca =  -40;


    private Resources resources;


    public DistanciaSensor(){
        resources = MainActivity.getContext().getResources();
    }

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
