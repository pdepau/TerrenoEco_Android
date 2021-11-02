package com.example.admallla.shumo;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Utilidades.java
 * <p>
 * AUTOR: Adrian Maldonado Llambies
 * FECHA: 17/10/2021
 * DESCRIPCION: Clase para la conversion de bits
 */

public class Utilidades {

    /**
     * Convertir string a bites
     *
     * @param texto
     * @return
     */
    public static byte[] stringToBytes(String texto) {
        return texto.getBytes();
        // byte[] b = string.getBytes(StandardCharsets.UTF_8); // Ja
    } // ()


    /**
     * Convertir de string a UUID
     *
     * @param uuid
     * @return
     */
    public static UUID stringToUUID(String uuid) {
        if (uuid.length() != 16) {
            throw new Error("stringUUID: string no tiene 16 caracteres ");
        }
        byte[] comoBytes = uuid.getBytes();

        String masSignificativo = uuid.substring(0, 8);
        String menosSignificativo = uuid.substring(8, 16);
        UUID res = new UUID(Utilidades.bytesToLong(masSignificativo.getBytes()), Utilidades.bytesToLong(menosSignificativo.getBytes()));

        // Log.d( MainActivity.ETIQUETA_LOG, " \n\n***** stringToUUID *** " + uuid  + "=?=" + Utilidades.uuidToString( res ) );

        // UUID res = UUID.nameUUIDFromBytes( comoBytes ); no va como quiero

        return res;
    } // ()

    /**
     * UUID a String
     *
     * @param uuid
     * @return
     */
    public static String uuidToString(UUID uuid) {
        return bytesToString(dosLongToBytes(uuid.getMostSignificantBits(), uuid.getLeastSignificantBits()));
    } // ()


    /**
     * UUID a Hexadecimal
     *
     * @param uuid
     * @return
     */
    public static String uuidToHexString(UUID uuid) {
        return bytesToHexString(dosLongToBytes(uuid.getMostSignificantBits(), uuid.getLeastSignificantBits()));
    } // ()


    /**
     * bytes a String
     *
     * @param bytes
     * @return
     */
    public static String bytesToString(byte[] bytes) {
        if (bytes == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append((char) b);
        }
        return sb.toString();
    }


    /**
     * Long a bytes
     *
     * @param masSignificativos
     * @param menosSignificativos
     * @return
     */
    public static byte[] dosLongToBytes(long masSignificativos, long menosSignificativos) {
        ByteBuffer buffer = ByteBuffer.allocate(2 * Long.BYTES);
        buffer.putLong(masSignificativos);
        buffer.putLong(menosSignificativos);
        return buffer.array();
    }


    /**
     * Bytes a int
     *
     * @param bytes
     * @return
     */
    public static int bytesToInt(byte[] bytes) {
        return new BigInteger(bytes).intValue();
    }


    /**
     * Bytes a Long
     *
     * @param bytes
     * @return
     */
    public static long bytesToLong(byte[] bytes) {
        return new BigInteger(bytes).longValue();
    }


    /**
     * Bytes a int
     *
     * @param bytes
     * @return
     */
    public static int bytesToIntOK(byte[] bytes) {
        if (bytes == null) {
            return 0;
        }

        if (bytes.length > 4) {
            throw new Error("demasiados bytes para pasar a int ");
        }
        int res = 0;


        for (byte b : bytes) {
            res = (res << 8) // * 16
                    + (b & 0xFF); // para quedarse con 1 byte (2 cuartetos) de lo que haya en b
        } // for

        if ((bytes[0] & 0x8) != 0) {
            // si tiene signo negativo (un 1 a la izquierda del primer byte
            res = -(~(byte) res) - 1; // complemento a 2 (~) de res pero como byte, -1
        }

        return res;
    } // ()


    /**
     * bytes a Hexadecimal
     *
     * @param bytes
     * @return
     */
    public static String bytesToHexString(byte[] bytes) {

        if (bytes == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
            sb.append(':');
        }
        return sb.toString();
    } // ()
} // class
// -----------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------
