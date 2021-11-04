package com.lbelmar.biometric;

// -------------------------------------------------------
// Autor: Luis Belloch
// Descripcion: Objeto medida para administrar datos
// Fecha: 15/10/2021
// -------------------------------------------------------
import java.time.LocalDateTime;

public class Medida {

    int medicion_valor;
    String fechaConFormato;
    double latitud;
    double longitud;

    /**
     * Constructor de un objeto Medida
     * @param medicion_valor medicion CO2
     * @param latitud en grados
     * @param longitud en grados
     */
    public Medida(int medicion_valor, double latitud, double longitud) {
        this.fechaConFormato = "2021-11-01 20:23:44.000000";
        this.medicion_valor = medicion_valor;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    /**
     * Convierte el objeto en texto
     *
     * @return json en texto
     */
    @Override
    public String toString() {
        String res = "{" +
                "\"valor\":\""+this.medicion_valor+ "\", " +
                "\"fecha\":\""+fechaConFormato+"\", " +
                "\"latitud\":\""+this.latitud+"\", " +
                "\"longitud\":\""+this.longitud+"\"" +
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
    public String getFechaConFormato() {
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
