package com.example.miwidgetbateria2;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.icu.text.UnicodeSetSpanner;
import android.os.BatteryManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class Widget extends AppWidgetProvider {

    @Override
    public void onEnabled(Context context)
    {
        Log.v("WIDGETT", "Nueva instancia del widget");
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {

        for(int i = 0; i < appWidgetIds.length; i++)
        {
            cargarDatosIniciales(context, appWidgetManager, appWidgetIds[i]);
        }

        Log.v("WIDGET", "Estamos en el método onUpdate");
    }

    public static void cargarDatosIniciales(Context configuracion, AppWidgetManager appWidgetManager, int numeroWidget)
    {
        SharedPreferences prefs = configuracion.getSharedPreferences("Prefs", Context.MODE_PRIVATE);

        String cabecera = prefs.getString("cabecera " + numeroWidget, "% restante");

        RemoteViews remoteViews = new RemoteViews(configuracion.getPackageName(), R.layout.widget);

        remoteViews.setTextViewText(R.id.txtMensaje, cabecera);

        remoteViews.setProgressBar(R.id.progress_bar, 100, obtenerNivelCargaBateria(configuracion), false);

        appWidgetManager.updateAppWidget(numeroWidget, remoteViews);
    }



    public static int obtenerNivelCargaBateria(Context context)
    {
        try
        {
            IntentFilter batIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent battery = context.registerReceiver(null, batIntentFilter);
            int nivelBateria = battery.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);

            CharSequence text = "El nivel actual de bateria es: " + nivelBateria;

            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return  nivelBateria;
        }
        catch (Exception e)
        {
            CharSequence text = "No se ha podido conocer el estado de la batería";

            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);

            toast.show();

            return 0;
        }
    }













}