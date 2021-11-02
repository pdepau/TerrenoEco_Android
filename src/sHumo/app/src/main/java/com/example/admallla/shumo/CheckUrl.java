package com.example.admallla.shumo;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * CheckUrl.java
 * <p>
 * AUTOR: Adrian Maldonado Llambies
 * FECHA: 17/10/2021
 * DESCRIPCION: Clase para las respuestas HTTP
 */
public class CheckUrl extends AsyncTask<String, Void, String> {

    String directionMode = "driving";

    public CheckUrl() {

    }

    /**
     * Comprueba en segundo plano la peticion http
     *
     * @param strings
     * @return
     */
    @Override
    protected String doInBackground(String... strings) {
        // For storing data from web service
        String data = "";
        directionMode = strings[1];
        try {
            // Fetching the data from web service
            if (strings[1].equals("GET")) {
                data = downloadUrl(strings[0]);
                Log.d("mylog", "Background task data " + data.toString());
            } else {
                data = postUrl(strings[0]);
                Log.d("mylog", "Background task data " + data.toString());
            }

        } catch (Exception e) {
            Log.d("Background Task", e.toString());
        }
        return data;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("URL", s);
    }

    /**
     * Devuelve la lista de Medidas
     *
     * @param strUrl Url dde la peticion
     * @return
     * @throws IOException
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
            // Connecting to url
            urlConnection.connect();
            // Reading data from url
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            Log.d("mylog", "Downloaded URL: " + data.toString());
            br.close();
        } catch (Exception e) {
            Log.d("mylog", "Exception downloading URL: " + e.toString());
        } finally {
            //iStream.close();
            urlConnection.disconnect();

        }
        return data;
    }

    /**
     * Envia la peticion post al servidor
     *
     * @param strUrl Url con los datos
     * @return
     * @throws IOException
     */
    private String postUrl(String strUrl) throws IOException {
        String data = "";

        Double valor = Math.random() * 100;

        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            //poner las cabeceras
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setDoOutput(true);
            //conexion a la base de datos
            urlConnection.connect();

            // convertir la respuesta en String
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                Log.d("", response.toString());
            }

        } catch (Exception e) {
            Log.d("mylog", "Exception downloading URL: " + e.toString());
        } finally {
            urlConnection.disconnect();
        }
        return data;
    }
}
