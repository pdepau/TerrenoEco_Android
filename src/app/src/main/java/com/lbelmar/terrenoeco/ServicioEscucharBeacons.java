package com.lbelmar.terrenoeco;
// -------------------------------------------------------
// Autor: Luis Belloch
// Descripcion: Administra el servicio de escuchar beacons
// Fecha: 15/10/2021
// -------------------------------------------------------

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.lbelmar.terrenoeco.ui.sensor.SensorFragment;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------
public class ServicioEscucharBeacons extends IntentService implements LocationListener {

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    private static final String ETIQUETA_LOG = ">>>>";

    private boolean seguir = true;

    //Gestion de la notificacion del servicio
    GestionNotificaciones gestorNotidicaciones;
    DistanciaSensor distanciaSensor = new DistanciaSensor();
    int idNotificacionServicio;
    int idNotificacionAlertaMedida = 0;
    Intent elIntentDelServicio;

    //Deteccion de problemas en el beacon
    long contador = 1;
    // --------------------------------------------------------------
    // --------------------------------------------------------------
    //Escaner bluetooth
    private BluetoothLeScanner elEscanner;
    private ScanCallback callbackDelEscaneo = null;

    //Distancia dispositivo-movil
    private float mediaDistanciaSensorAlMovil = -10;

    //Media diaria
    public static MediaDiaria mediaDiaria;

    //Localizacion
    static LocationManager manejador;
    static Location ultimaLocalizacion;

    //Calibración
    public static int ultimaMedida;
    public static int valorCalibracion;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    public ServicioEscucharBeacons() {
        super("HelloIntentService");

        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.constructor: termina");

    }

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------

    /**
     * Para el servicio
     */
    public void parar() {

        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.parar() ");

        if (this.seguir == false) {
            return;
        }

        gestorNotidicaciones.quitarNotificacion(idNotificacionServicio);
        detenerBusquedaDispositivosBTLE();
        SensorFragment.actualizarTextoMedida("Servicio parado");
        this.seguir = false;
        this.stopSelf();

        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.parar() : acaba ");

    }

    // -----r----------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    public void onDestroy() {

        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onDestroy() ");

        this.parar(); // posiblemente no haga falta, si stopService() ya se carga el servicio y su worker thread
    }

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------

    /**
     * The IntentService calls this method from the default worker thread with
     * the intent that started the service. When this method returns, IntentService
     * stops the service, as appropriate.
     *
     * @param intent Intent
     */
    @SuppressLint("MissingPermission")
    @Override
    protected void onHandleIntent(Intent intent) {

        SensorFragment.actualizarTextoMedida("Servicio arrancado");

        gestorNotidicaciones = new GestionNotificaciones();
        /* default */
        long tiempoDeEspera = intent.getLongExtra("tiempoDeEspera", /* default */ 50000);
        this.seguir = true;

        elIntentDelServicio = intent;
        idNotificacionServicio = gestorNotidicaciones.crearNotificacionServicio(elIntentDelServicio, "Servicio", "TExto y tal");

        sharedPref = MainActivity.getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        valorCalibracion = sharedPref.getInt(MainActivity.getActivity().getString(R.string.nombre_clave_valor_calibracion), 0);


        mediaDiaria = new MediaDiaria();
        empezarServicioLocalizacion();
        inicializarBlueTooth();

        // esto lo ejecuta un WORKER THREAD !
        //buscarTodosLosDispositivosBTLE();
        buscarEsteDispositivoBTLE(Constantes.NOMBRE_SENSOR);

        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onHandleIntent: empieza : thread=" + Thread.currentThread().getId());

        try {

            while (this.seguir) {
                Log.d("contador", " ServicioEscucharBeacons.onHandleIntent: tras la espera:  " + contador);
                contador++;

                if (contador > 1) {
                    gestorNotidicaciones.actualizarNotificacionServicio(idNotificacionServicio, elIntentDelServicio, "Problema con el sensor", "Puede que el sensor se haya desconectado, revise la conexión ");
                }
                Thread.sleep(tiempoDeEspera);
            }

            Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onHandleIntent : tarea terminada ( tras while(true) )");

        } catch (InterruptedException e) {
            // Restore interrupt status.
            Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onHandleItent: problema con el thread");

            Thread.currentThread().interrupt();
        }

        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onHandleItent: termina");

    }
    // --------------------------------------------------------------
    // --------------------------------------------------------------

    /**
     * Busca los dispositivos disponibles y despues recibe los datos de los sensores
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void buscarTodosLosDispositivosBTLE() {
        Log.d(ETIQUETA_LOG, " buscarTodosLosDispositivosBTL(): empieza ");

        Log.d(ETIQUETA_LOG, " buscarTodosLosDispositivosBTL(): instalamos scan callback ");

        this.callbackDelEscaneo = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult resultado) {
                super.onScanResult(callbackType, resultado);
                Log.d(ETIQUETA_LOG, " buscarTodosLosDispositivosBTL(): onScanResult() ");

                mostrarInformacionDispositivoBTLE(resultado);
            }

            @Override
            public void onBatchScanResults(List<ScanResult> results) {
                super.onBatchScanResults(results);
                Log.d(ETIQUETA_LOG, " buscarTodosLosDispositivosBTL(): onBatchScanResults() ");

            }

            @Override
            public void onScanFailed(int errorCode) {
                super.onScanFailed(errorCode);
                Log.d(ETIQUETA_LOG, " buscarTodosLosDispositivosBTL(): onScanFailed() ");

            }
        };

        Log.d(ETIQUETA_LOG, " buscarTodosLosDispositivosBTL(): empezamos a escanear ");

        this.elEscanner.startScan(this.callbackDelEscaneo);

    } // ()

    // --------------------------------------------------------------
    // --------------------------------------------------------------

    /**
     * Muestra la informacion de un dispositivo
     *
     * @param dispositivo el dispositivo a analizar
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void mostrarInformacionDispositivoBTLE(ScanResult dispositivo) {

        BluetoothDevice bluetoothDevice = dispositivo.getDevice();
        byte[] bytes = dispositivo.getScanRecord().getBytes();
        int rssi = dispositivo.getRssi();

        Log.d(ETIQUETA_LOG, " ****************************************************");
        Log.d(ETIQUETA_LOG, " ****** DISPOSITIVO DETECTADO BTLE ******************");
        Log.d(ETIQUETA_LOG, " ****************************************************");
        Log.d(ETIQUETA_LOG, " nombre = " + bluetoothDevice.getName());
        Log.d(ETIQUETA_LOG, " toString = " + bluetoothDevice.toString());
        Log.d(ETIQUETA_LOG, " dirección = " + bluetoothDevice.getAddress());
        Log.d(ETIQUETA_LOG, " rssi = " + rssi);
        Log.d(ETIQUETA_LOG, " bytes = " + new String(bytes));
        Log.d(ETIQUETA_LOG, " bytes (" + bytes.length + ") = " + Utilidades.bytesToHexString(bytes));

        TramaIBeacon tib = new TramaIBeacon(bytes);

        Log.d(ETIQUETA_LOG, " ----------------------------------------------------");
        Log.d(ETIQUETA_LOG, " prefijo  = " + Utilidades.bytesToHexString(tib.getPrefijo()));
        Log.d(ETIQUETA_LOG, "          advFlags = " + Utilidades.bytesToHexString(tib.getAdvFlags()));
        Log.d(ETIQUETA_LOG, "          advHeader = " + Utilidades.bytesToHexString(tib.getAdvHeader()));
        Log.d(ETIQUETA_LOG, "          companyID = " + Utilidades.bytesToHexString(tib.getCompanyID()));
        Log.d(ETIQUETA_LOG, "          iBeacon type = " + Integer.toHexString(tib.getiBeaconType()));
        Log.d(ETIQUETA_LOG, "          iBeacon length 0x = " + Integer.toHexString(tib.getiBeaconLength()) + " ( "
                + tib.getiBeaconLength() + " ) ");
        Log.d(ETIQUETA_LOG, " uuid  = " + Utilidades.bytesToHexString(tib.getUUID()));
        Log.d(ETIQUETA_LOG, " uuid  = " + Utilidades.bytesToString(tib.getUUID()));
        Log.d(ETIQUETA_LOG, " major  = " + Utilidades.bytesToHexString(tib.getMajor()) + "( "
                + Utilidades.bytesToInt(tib.getMajor()) + " ) ");
        Log.d(ETIQUETA_LOG, " minor  = " + Utilidades.bytesToHexString(tib.getMinor()) + "( "
                + Utilidades.bytesToInt(tib.getMinor()) + " ) ");
        Log.d(ETIQUETA_LOG, " txPower  = " + Integer.toHexString(tib.getTxPower()) + " ( " + tib.getTxPower() + " )");
        Log.d(ETIQUETA_LOG, " ****************************************************");

    } // ()

    // --------------------------------------------------------------
    // --------------------------------------------------------------

    /**
     * Busca un dispositivo concreto
     *
     * @param dispositivoBuscado direccion MAC del dispositivo
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void buscarEsteDispositivoBTLE(final String dispositivoBuscado) {
        Log.d(ETIQUETA_LOG, " buscarEsteDispositivoBTLE(): empieza ");

        Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): instalamos scan callback ");

        // super.onScanResult(ScanSettings.SCAN_MODE_LOW_LATENCY, result); para ahorro de energía

        this.callbackDelEscaneo = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult resultado) {
                super.onScanResult(callbackType, resultado);
                Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): onScanResult() ");
                contador = 0;
                Date date = new Date();

                //mostrarInformacionDispositivoBTLE(resultado);

                byte[] bytes = resultado.getScanRecord().getBytes();
                TramaIBeacon tib = new TramaIBeacon(bytes);

                Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): dispositivo escaneado UUID:  " + Arrays.toString(tib.getUUID()));
                // Esto es el valor de la medida

                ultimaMedida = Utilidades.bytesToInt(tib.getMinor());

                int medidaCalibrada = ultimaMedida + valorCalibracion;

                mediaDistanciaSensorAlMovil += (resultado.getRssi() - mediaDistanciaSensorAlMovil) / 4;
                String distancia = distanciaSensor.distanciaRelativa(mediaDistanciaSensorAlMovil);
                SensorFragment.actualizarTextoDistancia(distancia);


                Integer valorUmbralAlto = SensorFragment.umbralAlto;
                if (medidaCalibrada > valorUmbralAlto) {
                    if (idNotificacionAlertaMedida == 0) {
                        idNotificacionAlertaMedida = gestorNotidicaciones.crearNotificacionAlertas(Color.RED, "ZONA ALTAMENTE CONTAMINADA", "Se ha detectado un valor de " + medidaCalibrada + " en la zona.");
                    }
                    gestorNotidicaciones.actualizarNotificacionAlertas(idNotificacionAlertaMedida, "ZONA ALTAMENTE CONTAMINADA", "Se ha detectado un valor de " + medidaCalibrada + " en la zona.");
                }

                gestorNotidicaciones.actualizarNotificacionServicio(idNotificacionServicio, elIntentDelServicio, "Nueva medida", "Fecha " + date.toString());

                // Objeto medicion constructor
                if (getLocation() != null){
                    Medida medida = new Medida(medidaCalibrada, getLocation().getLatitude(), getLocation().getLongitude());
                    Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): medida" + medida.toString());

                    // Envia el objeto por la logica
                    Logica.guardarMedida(medida);

                    // Actualiza la media
                    mediaDiaria.actualizarMedia(medida.medicion_valor);
                    SensorFragment.actualizarTextoMedida(mediaDiaria.getMedia() + "");
                }else{
                    empezarServicioLocalizacion();
                }

            }

            @Override
            public void onBatchScanResults(List<ScanResult> results) {
                super.onBatchScanResults(results);
                Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): onBatchScanResults() ");
            }

            @Override
            public void onScanFailed(int errorCode) {
                super.onScanFailed(errorCode);
                Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): onScanFailed() ");
            }
        };

        List<ScanFilter> sf = Collections.singletonList(new ScanFilter.Builder().setDeviceName(dispositivoBuscado).build());
        ScanSettings settings = new ScanSettings.Builder().setScanMode(ScanSettings.CALLBACK_TYPE_ALL_MATCHES).build();

        Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): empezamos a escanear buscando: " + dispositivoBuscado);

        this.elEscanner.startScan(sf, settings, this.callbackDelEscaneo);
    } // ()

    // --------------------------------------------------------------
    // --------------------------------------------------------------

    /**
     * Detiene la busqueda de un dispositivo
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void detenerBusquedaDispositivosBTLE() {

        if (this.callbackDelEscaneo == null) {
            return;
        }

        this.elEscanner.stopScan(this.callbackDelEscaneo);
        this.callbackDelEscaneo = null;

    } // ()


    // --------------------------------------------------------------
    // --------------------------------------------------------------

    /**
     * Inicia el servicio Bluetooth
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void inicializarBlueTooth() {
        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): obtenemos adaptador BT ");

        BluetoothAdapter bta = BluetoothAdapter.getDefaultAdapter();

        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): habilitamos adaptador BT ");

        bta.enable();

        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): habilitado =  " + bta.isEnabled());

        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): estado =  " + bta.getState());

        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): obtenemos escaner btle ");

        this.elEscanner = bta.getBluetoothLeScanner();

        if (this.elEscanner == null) {
            Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): Socorro: NO hemos obtenido escaner btle  !!!!");

        }

        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): voy a perdir permisos (si no los tuviera) !!!!");

    } // ()

    Criteria criterio = new Criteria();
    static String proveedor;
    @SuppressLint("MissingPermission")
    public void empezarServicioLocalizacion() {
        manejador = (LocationManager) MainActivity.getContext().getSystemService(LOCATION_SERVICE);

        criterio.setCostAllowed(false);
        criterio.setAltitudeRequired(false);
        criterio.setAccuracy(Criteria.ACCURACY_FINE);
        proveedor = manejador.getBestProvider(criterio, true);
        ultimaLocalizacion = manejador.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        manejador.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, this::onLocationChanged);



    }

    public static Location getLocation() {
        ultimaLocalizacion = manejador.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (ultimaLocalizacion!=null){
            MainActivity.centrarMapaEnUbicacion(ultimaLocalizacion.getLatitude(),ultimaLocalizacion.getLongitude());
        }


        return ultimaLocalizacion;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        ultimaLocalizacion = location;
        MainActivity.centrarMapaEnUbicacion(location.getLatitude(),location.getLongitude());
        Log.d("Localizacion", location.toString()+"123");
    }

} // class
// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------