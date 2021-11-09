package com.lbelmar.terrenoeco;

public class DistanciaSensor {

    float umbralMuyAlto =  -80;
    float umbralAlto =  -75;
    float umbralCerca =  -40;

    public String distanciaRelativa(float valor){

        if(valor < umbralMuyAlto){
            return "Muy lejos";
        }else if(valor < umbralAlto){
            return "Lejos";
        }else if(valor < umbralCerca){
            return "Cerca";
        }else {
            return "Muy cerca";
        }

    }

}
