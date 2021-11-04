package com.lbelmar.biometric;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Date;

public class GestionNotificaciones {

    private static final String CHANNEL_ID = "123123123";

    NotificationManagerCompat notificationManager;

    public GestionNotificaciones() {

        notificationManager = NotificationManagerCompat.from(MainActivity.getContext());
        createNotificationChannel();
    }

    public void createNotificationChannel() {


        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "alerta";
            String description = "notificacion de alertas";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager.createNotificationChannel(channel);
        }


    }

    public void crearNotificacionAlertas(int color, String titulo, String descripcion) {

        Intent intent = new Intent(MainActivity.getContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.getContext(), 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.getContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setColor(color)
                .setContentTitle(titulo)
                .setContentText(descripcion)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        // notificationId is a unique int for each notification that you must define
        java.util.Date date = new Date();

        int notificationId = Integer.parseInt((date.getTime() / 1000) + "");
        notificationManager.notify(notificationId, builder.build());
    }

    public int crearNotificacionServicio(Intent intent, String titulo, String descripcion) {

        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.getContext(), 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.getContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(titulo)
                .setContentText(descripcion)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        // notificationId is a unique int for each notification that you must define
        java.util.Date date = new Date();
        int notificationId = Integer.parseInt((date.getTime() / 1000) + "");
        notificationManager.notify(notificationId, builder.build());
        return notificationId;
    }

    public void quitarNotificacion(int id){

        notificationManager.cancel(id);

    }

    public void actualizarNotificacionServicio(int idNotificacion,Intent intent, String titulo, String descripcion){
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.getContext(), 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.getContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(titulo)
                .setContentText(descripcion)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(idNotificacion,builder.build());
    }


}

