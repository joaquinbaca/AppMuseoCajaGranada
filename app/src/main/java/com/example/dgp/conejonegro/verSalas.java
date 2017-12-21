package com.example.dgp.conejonegro;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by alexr on 25/11/2017.
 */

public class verSalas extends AppCompatActivity implements Runnable{
    Museo museo;

    private Button botonListaSalas;
    private Button botonQR;
    private Button botonRutas;
    private Button botonConfig;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listasalas);

        progressDialog = ProgressDialog.show(this, "Cargando", "Descargando salas", true,
                false);

        Thread thread = new Thread(this);
        thread.start();

    }

    @Override
    public void run() {
        SharedPreferences config=getSharedPreferences("config", Context.MODE_PRIVATE);
        String idioma = config.getString("idioma","Español");
        boolean lengSimple = config.getBoolean("lenguajeSimple", false);
        boolean lengSignos = config.getBoolean("lenguajeSignos", false);
        museo = Museo.getInstance(idioma, lengSimple, lengSignos);
        SalasFragment salasFragment = (SalasFragment) getSupportFragmentManager().findFragmentById(R.id.salas_container);
        if (salasFragment == null) {
            salasFragment = SalasFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.salas_container, salasFragment).commit();
        }
        handler.sendEmptyMessage(0);
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            crearBotones();
            progressDialog.dismiss();
        }
    };

    public void crearBotones(){

        botonListaSalas = (Button)findViewById(R.id.salaBotonSalas);

        botonQR = (Button)findViewById(R.id.salaBotonQR);

        botonRutas = (Button)findViewById(R.id.salasBotonRutas);

        botonConfig = (Button)findViewById(R.id.salasconfiguracionBoton);

        botonConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(verSalas.this, Configuracion.class));
                finish();
            }
        });

        botonQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(verSalas.this, lector.class));
                finish();
            }
        });

        botonRutas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(Principal.this, Configuracion.class));
                //finish();
            }
        });
    }
}
