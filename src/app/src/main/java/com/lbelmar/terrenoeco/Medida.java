package com.lbelmar.terrenoeco;

// -------------------------------------------------------
// Autor: Luis Belloch
// Descripcion: Objeto medida para administrar datos
// Fecha: 15/10/2021
// -------------------------------------------------------

import java.util.Date;

public class Medida {

    int medicion_valor;
    long fechaConFormato;
    double latitud;
    double longitud;
    int usuario;
    int tipo;
    int nodo;

    Date date;
    /**
     * Constructor de un objeto Medida
     * @param medicion_valor medicion CO2
     * @param latitud en grados
     * @param longitud en grados
     */
    public Medida(int medicion_valor, double latitud, double longitud) {
        date = new Date();
        this.fechaConFormato = date.getTime();
        this.medicion_valor = medicion_valor;
        this.latitud = latitud;
        this.longitud = longitud;
        this.usuario = 1;
        this.tipo = 1;
        this.nodo = 1;
    }

    /**
     * Convierte el objeto en texto
     *
     * @return json en texto
     */
    @Override
    public String toString() {
        String res = "{" +
                "\"valor\":"+this.medicion_valor+ ", " +
                "\"tiempo\":\""+this.fechaConFormato+"\", " +
                "\"latitud\":\""+this.latitud+"\", " +
                "\"longitud\":\""+this.longitud+"\"," +
                "\"usuario\":"+this.usuario+"," +
                "\"tipo\":"+this.tipo+"," +
                "\"nodo\":"+this.nodo+"" +
                "}";
        return res;
    }

    // -------------------------------------------------------
    // region GETTERS
    // -------------------------------------------------------
    /**
     * getMedicion_valor() -> medicion_valor:Z
     * Devuelve el valor de la medida
     *
     * @return Z con el valor de CO2
     */
    public int getMedicion_valor() {
        return medicion_valor;
    }

    /**
     * getFechaConFormato() -> fechaConFormato:texto
     * Devuelve la fecha en formato DateTime
     *
     * @return fecha en formato DateTime
     */
    public Long getFechaConFormato() {
        return fechaConFormato;
    }

    /**
     * getLatitud() -> latitud:Z
     * Devuelve el valor de latitud
     *
     * @return Z con el valor de CO2
     */
    public double getLatitud() {
        return latitud;
    }

    /**
     * getLongitud() -> longitud:Z
     * Devuelve el valor de longitud
     *
     * @return entero de longitud
     */
    public double getLongitud() {
        return longitud;
    }
    // -------------------------------------------------------
    // endregion
    // -------------------------------------------------------

}
