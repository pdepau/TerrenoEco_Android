package com.example.admallla.shumo;

/**
 * Medida.java
 * <p>
 * AUTOR: Adrian Maldonado Llambies
 * FECHA: 17/10/2021
 * DESCRIPCION: Clase medida
 */
public class Medida {

    String fecha;
    int valor;
    String lat;
    String lon;

    /**
     * Constructor
     *
     * @param fecha
     * @param valor
     * @param lat
     * @param lon
     */
    public Medida(String fecha, int valor, String lat, String lon) {
        this.fecha = fecha;
        this.valor = valor;
        this.lat = lat;
        this.lon = lon;

    }

    /**
     * String formateado en JSON
     *
     * @return
     */
    @Override
    public String toString() {
        return "{\"fecha\":\"" + fecha + "\" , \"valor\": \" " + valor + "\", \"lat\": \"" + lat + "\", \"lon\": \"" + lon + "\"}";

    }
}
