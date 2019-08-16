package com.example.miwidgetbateria2;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Configuracion extends Activity {

    private int numeroWidget = 0;
    private Button botonAceptar;
    private Button botonCancelar;
    private TextView textoCabecera;
    private EditText nuevaCabecera;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuration_activity);

        Context context = getApplicationContext();
        CharSequence text = "Configuración del widget";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        Intent intent = getIntent();
        Bundle atributosIntent = intent.getExtras();

        numeroWidget = atributosIntent.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        setResult(RESULT_CANCELED);

        botonCancelar = (Button)findViewById(R.id.BotonCancelar);
        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                finish();
            }
        });

        botonAceptar = (Button)findViewById(R.id.BotonAceptar);
        botonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nuevaCabecera = (EditText) findViewById(R.id.MensajeCabecera);
                SharedPreferences prefs = getSharedPreferences("Prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();

                editor.putString("cabecera " + numeroWidget, nuevaCabecera.getText().toString());
                editor.commit();

                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(Configuracion.this);

                Widget.cargarDatosIniciales(Configuracion.this, appWidgetManager, numeroWidget);

                Intent resultado = new Intent();
                resultado.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, numeroWidget);
                setResult(RESULT_OK, resultado);

                finish();

            }
        });
    }
}
