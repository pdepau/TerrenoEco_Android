/**
 * AUTOR: Adrian Maldonado Llambies
 * FECHA: 17/10/2021
 * DESCRIPCION: Clase para la comunicacion con la base de datos
 */
package com.example.admallla.shumo;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * LogicaFake.java
 *
 * AUTOR: Adrian Maldonado Llambies
 * FECHA: 17/10/2021
 * DESCRIPCION: Clase logica del servidor
 *
 */

public class LogicaFake {

    private String ip = "10.236.14.211";

    public LogicaFake() {

    }

    /**
     *  Devuelve una lista con las últimas mediciones
     *
     *
     * @param cuantas Cantidad de medidas a devolver
     * @return
     */
    public ArrayList<Medida> obtenerUltimasMediciones(int cuantas) {

        ArrayList<Medida> medidas = new ArrayList<>();

        Thread thread = new Thread(() -> {
            try {
                CheckUrl check = new CheckUrl();
                try {
                    String s = check.doInBackground("http://" + ip + ":8080/medida/cuantas/" + cuantas, "GET");

                    medidas.clear();
                    Medida[] m = new Gson().fromJson(s, Medida[].class);
                    medidas.addAll(Arrays.asList(m));

                    Log.d("", m[0].toString());

                } catch (Exception e) {
                    Log.d("", e.toString());
                }
                //Your code goes here
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        thread.start();
        while (thread.getState() != Thread.State.TERMINATED) {
            Log.d("", "Esperar");
        }
        return medidas;
    }


    /**
     *
     * Lista de todas las medidas en la base de datos
     *
     * @return
     */
    public ArrayList<Medida> ObtenerTodasLasMediciones() {

        ArrayList<Medida> medidas = new ArrayList<>();

        Thread thread = new Thread(() -> {
            try {
                CheckUrl check = new CheckUrl();
                try {
                    String s = check.doInBackground("http://" + ip + ":8080/medida", "GET");

                    medidas.clear();
                    Medida[] m = new Gson().fromJson(s, Medida[].class);
                    medidas.addAll(Arrays.asList(m));

                    Log.d("", m[0].toString());

                } catch (Exception e) {
                    Log.d("", e.toString());
                }
                //Your code goes here
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        thread.start();
        while (thread.getState() != Thread.State.TERMINATED) {
            Log.d("", "Esperar");
        }
        return medidas;
    }

    /**
     *
     * Inserta la Medida en la base de datos
     *
     * @param m Objeto medida a insertar
     */
    public void insertarMedida(Medida m) {
        Thread thread1 = new Thread(() -> {
            try {
                CheckUrl check = new CheckUrl();
                try {
                    String s = check.doInBackground("http://" + ip + ":8080/medida/" + m.toString(), "POST");
                    Log.d("", s);

                } catch (Exception e) {
                    Log.d("", e.toString());
                }
                //Your code goes here
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread1.start();
    }

}
