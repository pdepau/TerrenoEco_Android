package com.lbelmar.terrenoeco;
// -------------------------------------------------------
// Autor: Luis Belloch
// Descripcion: Logica fake para acceder a la API
// Fecha: 15/10/2021
// -------------------------------------------------------

import android.util.Log;

import com.lbelmar.terrenoeco.ui.sensor.SensorFragment;

public class Logica {

    /**
     * obtenerTodasLasMedidas() -> [JSON]
     * Recibe las medidas del servidor REST
     */
    public static void obtenerTodasLasMedidas() { // Debe ser static para poder ser llamado desde el servicio
        new PeticionarioREST().hacerPeticionREST("GET", Constantes.URL + "mediciones", null, new PeticionarioREST.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {
                Log.d("REST", cuerpo);
            }
        });
    }

    /**
     * obtenerUltimaMedida() -> [JSON]
     * Recibe la ultima medida de la base de datos
     */
    public static void obtenerUltimaMedida() {
        new PeticionarioREST().hacerPeticionREST("GET", Constantes.URL + "mediciones/ultima", null, new PeticionarioREST.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {
                Log.d("REST", cuerpo);
            }
        });
    }

    /**
     * medida:Medida -> guardarMedida()
     * Envia una medida al servidor REST
     *
     * @param medida objeto Medida
     */
    public static void guardarMedida(Medida medida) {
        Log.d("REST", "medida enviada: " + medida.toString());
        new PeticionarioREST().hacerPeticionREST("POST", Constantes.URL + "medicion", medida.toString(), new PeticionarioREST.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {
                Log.d("REST", "Medida guardada : " + cuerpo);
            }
        });
    }

    /**
     * id:Z =>
     * obtenerTipo()
     * Tipo:Tipo <=
     *
     * @param {number}   id del tipo a tomar de la base de datos
     * @param {callback} cb donde se recibe la respuesta
     */


    public static void obtenerTipo(int id) { // Debe ser static para poder ser llamado desde el servicio


        final PeticionarioREST.RespuestaREST[] a = {new PeticionarioREST.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {
                //Log.d("ADRA", cuerpo);
                SensorFragment.aaa(codigo,cuerpo);
            }
        }};

        new PeticionarioREST().hacerPeticionREST("GET", Constantes.URL + "tipo/" + id, null, a[0]);

    }




}
