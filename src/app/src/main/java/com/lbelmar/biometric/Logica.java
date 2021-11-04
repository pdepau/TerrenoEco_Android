package com.lbelmar.biometric;
// -------------------------------------------------------
// Autor: Luis Belloch
// Descripcion: Logica fake para acceder a la API
// Fecha: 15/10/2021
// -------------------------------------------------------

import android.util.Log;

import java.util.Arrays;

public class Logica {

    /**
     * obtenerTodasLasMedidas() -> [JSON]
     * Recibe las medidas del servidor REST
     */
    public static void obtenerTodasLasMedidas() { // Debe ser static para poder ser llamado desde el servicio
        new PeticionarioREST().hacerPeticionREST("GET", Constantes.URL + "medidas", null, new PeticionarioREST.RespuestaREST() {
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
        new PeticionarioREST().hacerPeticionREST("GET", Constantes.URL + "medida/ultima", null, new PeticionarioREST.RespuestaREST() {
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
        new PeticionarioREST().hacerPeticionREST("POST", Constantes.URL + "medida", medida.toString(), new PeticionarioREST.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) {
                Log.d("REST","Medida guardada : "+cuerpo);
            }
        });
    }
}
